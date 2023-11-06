package ee.qrental.transaction.core.service.rent;

import static ee.qrental.common.core.utils.QTimeUtils.getWeekNumber;
import static java.lang.Boolean.FALSE;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.common.core.utils.Week;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.email.api.in.request.EmailSendRequest;
import ee.qrental.email.api.in.request.EmailType;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
// import jakarta.transaction.Transactional;
import ee.qrental.transaction.api.in.usecase.rent.RentCalculationAddUseCase;
import ee.qrental.transaction.api.out.rent.RentCalculationAddPort;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.core.mapper.rent.RentCalculationAddRequestMapper;
import ee.qrental.transaction.core.service.TransactionUseCaseService;
import ee.qrental.transaction.core.validator.RentCalculationAddBusinessRuleValidator;
import ee.qrental.transaction.domain.rent.RentCalculationResult;
import ee.qrental.user.api.in.query.GetUserAccountQuery;
import ee.qrental.user.api.in.response.UserAccountResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationService implements RentCalculationAddUseCase {
  private static final String TRANSACTION_TYPE_NAME_WEEKLY_RENT = "weekly rent";
  private static final String TRANSACTION_TYPE_NO_LABEL_FINE = "no label fine";
  private static final BigDecimal OLD_CAR_RATE = BigDecimal.valueOf(150L);
  private static final BigDecimal NEW_CAR_RATE = BigDecimal.valueOf(230L);
  private static final BigDecimal ELEGANCE_RATE = BigDecimal.valueOf(10L);
  private static final BigDecimal LPG_RATE = BigDecimal.valueOf(10L);
  private static final BigDecimal NO_LABEL_RATE = BigDecimal.valueOf(20L);

  private final GetCarLinkQuery carLinkQuery;
  private final GetCarQuery carQuery;
  private final GetTransactionQuery transactionQuery;
  private final TransactionUseCaseService transactionUseCaseService;
  private final RentCalculationAddPort rentCalculationAddPort;
  private final RentCalculationAddRequestMapper addRequestMapper;
  private final TransactionTypeLoadPort transactionTypeLoadPort;
  private final RentCalculationAddBusinessRuleValidator addBusinessRuleValidator;
  private final EmailSendUseCase emailSendUseCase;
  private final GetUserAccountQuery userAccountQuery;
  private final GetQWeekQuery weekQuery;

  private static Week getWeekForRentCalculation(final LocalDate calculationDate) {
    // add One day, to make day of Week Tuesday, to avoid Localisation issues Sunday or Monday week
    // start
    final var weekNumber = getWeekNumber(calculationDate.plusDays(1L));
    final var week = new Week(calculationDate, calculationDate.plusDays(7L), weekNumber);

    return week;
  }

  // @Transactional
  @Override
  public void add(final RentCalculationAddRequest addRequest) {

    final var calculationStartTime = System.currentTimeMillis();
    final var violationsCollector = addBusinessRuleValidator.validate(addRequest);
    if (violationsCollector.hasViolations()) {
      addRequest.setViolations(violationsCollector.getViolations());
      return;
    }

    final var domain = addRequestMapper.toDomain(addRequest);
    final var calculationDate = addRequest.getActionDate();
    final var week = weekQuery.getById(1L);
    final var activeCarLinks = carLinkQuery.getActive();
    for (final CarLinkResponse activeCarLink : activeCarLinks) {
      final TransactionAddRequest rentTransactionAddRequest =
          getRentTransactionAddRequest(week, activeCarLink);
      final var carLinkId = activeCarLink.getId();
      final var rentTransactionId = transactionUseCaseService.add(rentTransactionAddRequest);
      final var calculationResultForRent = getResult(carLinkId, rentTransactionId);
      domain.getResults().add(calculationResultForRent);
      final var noLabelFineTransactionAddRequest =
          getNoLabelFineTransactionAddRequest(week, activeCarLink);
      final var noLabelFineTransactionId =
          transactionUseCaseService.add(noLabelFineTransactionAddRequest);

      final var calculationResultForNoLabelFine = getResult(carLinkId, noLabelFineTransactionId);
      domain.getResults().add(calculationResultForNoLabelFine);
    }
    rentCalculationAddPort.add(domain);
    sendEmails(domain.getResults(), week.getNumber());
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf("----> Time: Rent Calculation took %d milli seconds \n", calculationDuration);
  }

  private RentCalculationResult getResult(final Long carLinkId, final Long transactionId) {

    return RentCalculationResult.builder()
        .carLinkId(carLinkId)
        .transactionId(transactionId)
        .build();
  }
//TODO complete Qweek migration
  private TransactionAddRequest getRentTransactionAddRequest(
          final QWeekResponse week, final CarLinkResponse activeCarLink) {
    final var addRequest = new TransactionAddRequest();
    addRequest.setDate(LocalDate.now());
    final var transactionTpe =
        transactionTypeLoadPort.loadByName(TRANSACTION_TYPE_NAME_WEEKLY_RENT);
    if (transactionTpe == null) {
      throw new RuntimeException(
          "Transaction type for weekly Rent Calculation is missing. Create a Transaction Type with name: "
              + TRANSACTION_TYPE_NAME_WEEKLY_RENT);
    }
    addRequest.setWithVat(FALSE);
    addRequest.setTransactionTypeId(transactionTpe.getId());
    addRequest.setWeekNumber(week.getNumber());
    addRequest.setDriverId(activeCarLink.getDriverId());
    addRequest.setAmount(calculateRentTransactionAmount(activeCarLink));
    addRequest.setComment(
        format(
            "Automatically crated 'Rent' Transaction for active Car Link %d. Week %d",
            activeCarLink.getId(), week.getNumber()));

    return addRequest;
  }

  private TransactionAddRequest getNoLabelFineTransactionAddRequest(
      final QWeekResponse week, final CarLinkResponse activeCarLink) {
    final var addRequest = new TransactionAddRequest();
    addRequest.setDate(LocalDate.now());
    final var transactionTpe = transactionTypeLoadPort.loadByName(TRANSACTION_TYPE_NO_LABEL_FINE);
    if (transactionTpe == null) {
      throw new RuntimeException(
          "Transaction type for No Label Fine is missing. Create a Transaction Type with name: "
              + TRANSACTION_TYPE_NO_LABEL_FINE);
    }

    addRequest.setTransactionTypeId(transactionTpe.getId());
    addRequest.setWeekNumber(week.getNumber());
    addRequest.setDriverId(activeCarLink.getDriverId());
    addRequest.setAmount(NO_LABEL_RATE);
    addRequest.setWithVat(FALSE);
    addRequest.setComment(
        format(
            "Automatically crated 'No Label Fine' Transaction for active Car Link %d. Week %d",
            activeCarLink.getId(), week.getNumber()));

    return addRequest;
  }

  private BigDecimal calculateRentTransactionAmount(final CarLinkResponse carLink) {
    var rentAmount = ZERO;
    final var carId = carLink.getCarId();
    final var car = carQuery.getById(carId);
    final var carReleaseYear = car.getReleaseDate().getYear();
    if (carReleaseYear < 2019) {
      rentAmount = rentAmount.add(OLD_CAR_RATE);
    } else {
      rentAmount = rentAmount.add(NEW_CAR_RATE);
    }
    if (car.getElegance()) {
      rentAmount = rentAmount.add(ELEGANCE_RATE);
    }
    if (car.getLpg()) {
      rentAmount = rentAmount.add(LPG_RATE);
    }

    return rentAmount;
  }

  private void sendEmails(final List<RentCalculationResult> results, final Integer weekNumber) {
    final var operators = userAccountQuery.getAllByRoleName("operator");
    final var recipients = operators.stream().map(UserAccountResponse::getEmail).collect(toList());
    final var emailProperties = new HashMap<String, Object>();
    emailProperties.put("calculationType", "Weekly Rent");
    emailProperties.put("calculationDate", LocalDate.now());
    emailProperties.put("weekNumber", weekNumber);
    emailProperties.put(
        "transactions",
        transactionQuery.getAllByIds(
            results.stream().map(RentCalculationResult::getTransactionId).collect(toList())));

    final var emailSendRequest =
        EmailSendRequest.builder()
            .type(EmailType.CALCULATION)
            .recipients(recipients)
            .properties(emailProperties)
            .build();

    emailSendUseCase.sendEmail(emailSendRequest);
  }
}
