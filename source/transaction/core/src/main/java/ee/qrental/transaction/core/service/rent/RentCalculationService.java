package ee.qrental.transaction.core.service.rent;

import static ee.qrental.common.core.utils.QTimeUtils.getWeekNumber;
import static java.math.BigDecimal.ZERO;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.common.core.utils.Week;
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
import java.math.BigDecimal;
import java.time.LocalDate;
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
  private final TransactionUseCaseService transactionUseCaseService;
  private final RentCalculationAddPort rentCalculationAddPort;
  private final RentCalculationAddRequestMapper addRequestMapper;
  private final TransactionTypeLoadPort transactionTypeLoadPort;
  private final RentCalculationAddBusinessRuleValidator addBusinessRuleValidator;

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
    final var week = getWeekForRentCalculation(calculationDate);
    final var activeCarLinks = carLinkQuery.getActiveByDate(calculationDate);
    for (CarLinkResponse carLink : activeCarLinks) {
      final TransactionAddRequest rentTransactionAddRequest =
          getRentTransactionAddRequest(week, carLink);
      final var rentTransactionId = transactionUseCaseService.add(rentTransactionAddRequest);
      final var calculationResultForRent =
          RentCalculationResult.builder()
              .carLinkId(carLink.getId())
              .transactionId(rentTransactionId)
              .build();
      domain.getResults().add(calculationResultForRent);
      final var noLabelFineTransactionAddRequest =
          getNoLabelFineTransactionAddRequest(week, carLink);
      final var noLabelFineTransactionId =
          transactionUseCaseService.add(noLabelFineTransactionAddRequest);

      final var calculationResultForNoLabelFine =
          RentCalculationResult.builder()
              .carLinkId(carLink.getId())
              .transactionId(noLabelFineTransactionId)
              .build();
      domain.getResults().add(calculationResultForNoLabelFine);
    }
    rentCalculationAddPort.add(domain);
    final var calculationEndTime = System.currentTimeMillis();
    final var calculationDuration = calculationEndTime - calculationStartTime;
    System.out.printf("----> Time: Rent Calculation took %d milli seconds \n", calculationDuration);
  }

  private static Week getWeekForRentCalculation(final LocalDate calculationDate) {
    final var weekNumber = getWeekNumber(calculationDate.plusDays(1l));
    final var week = new Week(calculationDate, calculationDate.plusDays(7l), weekNumber);

    return week;
  }

  private TransactionAddRequest getRentTransactionAddRequest(
      final Week week, final CarLinkResponse carLink) {
    final var addRequest = new TransactionAddRequest();
    addRequest.setDate(LocalDate.now());
    final var transactionTpe =
        transactionTypeLoadPort.loadByName(TRANSACTION_TYPE_NAME_WEEKLY_RENT);
    if (transactionTpe == null) {
      throw new RuntimeException(
          "Transaction type for weekly Rent Calculation is missing. Create a Transaction Type with name: "
              + TRANSACTION_TYPE_NAME_WEEKLY_RENT);
    }

    addRequest.setTransactionTypeId(transactionTpe.getId());
    addRequest.setWeekNumber(week.weekNumber());
    addRequest.setDriverId(carLink.getDriverId());
    addRequest.setAmount(calculateRentTransactionAmount(carLink));
    addRequest.setComment("Automatically crated Transaction for Car Link");

    return addRequest;
  }

  private TransactionAddRequest getNoLabelFineTransactionAddRequest(
      final Week week, final CarLinkResponse carLink) {
    final var addRequest = new TransactionAddRequest();
    addRequest.setDate(LocalDate.now());
    final var transactionTpe = transactionTypeLoadPort.loadByName(TRANSACTION_TYPE_NO_LABEL_FINE);
    if (transactionTpe == null) {
      throw new RuntimeException(
          "Transaction type for No Label Fine is missing. Create a Transaction Type with name: "
              + TRANSACTION_TYPE_NO_LABEL_FINE);
    }

    addRequest.setTransactionTypeId(transactionTpe.getId());
    addRequest.setWeekNumber(week.weekNumber());
    addRequest.setDriverId(carLink.getDriverId());
    addRequest.setAmount(NO_LABEL_RATE);
    addRequest.setComment("Automatically crated Transaction for Car ?????Link");

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
}
