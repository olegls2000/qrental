package ee.qrent.billing.report.core.service;

import static ee.qrent.billing.transaction.api.in.utils.TransactionKindCodesConstant.TRANSACTION_KIND_POSITIVE_CODE;
import static ee.qrent.billing.transaction.api.in.utils.TransactionTypeCodesConstant.*;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.deposit.api.in.query.GetDepositQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.api.in.request.WeeklyReportSendByEmailRequest;
import ee.qrent.billing.report.api.in.request.WeeklyReportTypeIn;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportCalculationAddUseCase;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportSendByEmailUseCase;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationAddPort;
import ee.qrent.billing.report.core.mapper.WeeklyReportCalculationAddRequestMapper;
import ee.qrent.billing.report.core.validator.WeeklyReportCalculationAddRequestValidator;
import ee.qrent.billing.report.domain.*;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.query.filter.DriverAndPeriodAndKindCodesFilter;
import ee.qrent.billing.transaction.api.in.query.filter.DriverAndPeriodAndTypeCodesFilter;
import ee.qrent.billing.transaction.api.in.query.filter.DriverAndPeriodFilter;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class WeeklyReportCalculationUseCaseService implements WeeklyReportCalculationAddUseCase {

  private static final BigDecimal DEPOSIT_OBLIGATION = new BigDecimal(500);

  private final WeeklyReportCalculationAddRequestValidator addRequestValidator;
  private final WeeklyReportCalculationAddRequestMapper addRequestMapper;
  private final WeeklyReportCalculationAddPort addPort;
  private final GetObligationQuery obligationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetCarLinkQuery carLinkQuery;
  private final GetBalanceQuery balanceQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetContractQuery contractQuery;
  private final GetDepositQuery depositQuery;
  private final WeeklyReportSendByEmailUseCase sendByEmailUseCase;

  @Override
  public Long add(final WeeklyReportCalculationAddRequest request) {
    final var savedDomain = addCalculation(request);
    if (request.hasViolations()) {

      return null;
    }
    sendNotifications(savedDomain);

    return savedDomain.getId();
  }

  @Transactional
  private WeeklyReportCalculation addCalculation(final WeeklyReportCalculationAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var requestedQWeekId = request.getQWeekId();
    final var calculation = addRequestMapper.toDomain(request);
    final var requestedQWeek = qWeekQuery.getById(requestedQWeekId);

    driverQuery.getAll().parallelStream()
        .filter(DriverResponse::getNeedReport)
        .forEach(
            driver -> {
              final var driverId = driver.getId();
              final var qWeekId = request.getQWeekId();
              final var contract = contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
              final var activeCallSignLink =
                  callSignLinkQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
              if (contract == null || activeCallSignLink == null) {
                return;
              }

              final var weeklyReport = getWeeklyReport(driver, requestedQWeek, request.getType());
              final var reportTransactions =
                  getWeeklyReportTransactions(weeklyReport, driver.getId(), requestedQWeekId);
              calculation.getReportTransactionLinks().add(reportTransactions);
            });

    return addPort.add(calculation);
  }

  @Transactional
  private void sendNotifications(final WeeklyReportCalculation savedDomain) {
    savedDomain.getReportTransactionLinks().parallelStream()
        .forEach(
            link -> {
              final var reportId = link.getWeeklyReport().getId();
              final var emailSendRequest = new WeeklyReportSendByEmailRequest(reportId);
              sendByEmailUseCase.sendByEmail(emailSendRequest);
            });
  }

  private WeeklyReport getWeeklyReport(
      final DriverResponse driver,
      final QWeekResponse requestedQWeek,
      final WeeklyReportTypeIn reportType) {
    final var driverId = driver.getId();
    final var qWeekId = requestedQWeek.getId();
    final var contract = contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
    final var depositPaid = depositQuery.getPaidAmountByDriverId(driverId);

    final var balanceAmountOnDate = getBalanceAmountOnDate(requestedQWeek, reportType, driverId);
    final var balanceOnSunday = getBalanceOnSunday(requestedQWeek, driverId);
    final var currentObligationAmount = getCurrentObligationAmount(driverId, requestedQWeek);
    final var netAmountOnThursday = getNetAmountOnThursday(driverId, requestedQWeek);

    return WeeklyReport.builder()
        .type(WeeklyReportType.valueOf(reportType.name()))
        .qWeekId(qWeekId)
        .driverId(driverId)
        .callSignId(getCallSignId(driverId, qWeekId))
        .carId(getCarId(driverId, qWeekId))
        .startDate(requestedQWeek.getStart())
        .endDate(requestedQWeek.getEnd())
        .weeksCountTillEnd(contract.getWeeksToEnd())
        .depositObligation(DEPOSIT_OBLIGATION)
        .depositPaid(depositPaid)
        .obligationStatus(getWeeklyReportObligationStatusForMondayReport(driver, requestedQWeek))
        .currentObligationAmount(currentObligationAmount)
        .balanceAmountSunday(balanceOnSunday.getAmount())
        .feeAmountSunday(balanceOnSunday.getFeeAmount())
        .balanceAmountAtCalculationMoment(balanceAmountOnDate)
        .netAmountOnThursday(netAmountOnThursday)
        .transactionTypesVsAmount(
            getAmountsMap(driverId, requestedQWeek.getStart(), requestedQWeek.getEnd()))
        .comment("Automatically generated weekly report")
        .build();
  }

  private BigDecimal getNetAmountOnThursday(
      final Long driverId, final QWeekResponse requestedQWeek) {
    final var monday = requestedQWeek.getStart();
    final var thursday = requestedQWeek.getEnd().minusDays(3L);

    return getObligationInvolvedTransactionsSum(driverId, monday, thursday);
  }

  private BigDecimal getCurrentObligationAmount(Long driverId, QWeekResponse requestedQWeek) {
    final var mondayPreviousWeek = requestedQWeek.getStart().minusDays(7);
    final var monday = requestedQWeek.getStart();

    return getObligationInvolvedTransactionsSum(driverId, mondayPreviousWeek, monday);
  }

  private BigDecimal getObligationInvolvedTransactionsSum(
      final Long driverId, final LocalDate start, final LocalDate end) {
    final var obligationTransactionFilter =
        DriverAndPeriodAndTypeCodesFilter.builder()
            .driverId(driverId)
            .dateStart(start)
            .dateEnd(end)
            .typeCodes(
                Stream.of(
                        TRANSACTION_TYPE_NAME_WEEKLY_RENT_CODE,
                        TRANSACTION_TYPE_NO_LABEL_FINE_CODE,
                        TRANSACTION_TYPE_INNER_ADDITIONAL_INSURANCE_CODE)
                    .collect(Collectors.toSet()))
            .build();

    final var obligationSum =
        transactionQuery.getAllByFilter(obligationTransactionFilter).stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(ZERO, BigDecimal::add);

    final var positiveTransactionFilter =
        DriverAndPeriodAndKindCodesFilter.builder()
            .driverId(driverId)
            .dateStart(start)
            .dateEnd(end)
            .kindCodes(Stream.of(TRANSACTION_KIND_POSITIVE_CODE).collect(Collectors.toSet()))
            .build();

    final var positiveSum =
        transactionQuery.getAllByFilter(positiveTransactionFilter).stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(ZERO, BigDecimal::add);

    return obligationSum.add(positiveSum);
  }

  private Map<String, BigDecimal> getAmountsMap(
      final Long driverId, final LocalDate start, final LocalDate end) {
    final var filter =
        DriverAndPeriodFilter.builder().driverId(driverId).dateStart(start).dateEnd(end).build();

    return transactionQuery.getAllByFilter(filter).stream()
        .collect(
            groupingBy(
                TransactionResponse::getTypeCode,
                reducing(BigDecimal.ZERO, TransactionResponse::getRealAmount, BigDecimal::add)));
  }

  private BigDecimal getBalanceAmountOnDate(
      final QWeekResponse requestedQWeek,
      final WeeklyReportTypeIn reportType,
      final Long driverId) {
    LocalDate reportDate = null;
    switch (reportType) {
      case MONDAY_REPORT -> reportDate = requestedQWeek.getStart();
      case TUESDAY_REPORT -> reportDate = requestedQWeek.getStart().plusDays(1L);
      case WEDNESDAY_REPORT -> reportDate = requestedQWeek.getStart().plusDays(2L);
      case FRIDAY_REPORT -> reportDate = requestedQWeek.getStart().plusDays(4L);
    }
    final var rawBalanceOnReportDate = balanceQuery.getRawByDriverAndDate(driverId, reportDate);

    return rawBalanceOnReportDate.getAmount();
  }

  private BalanceResponse getBalanceOnSunday(
      final QWeekResponse requestedQWeek, final Long driverId) {
    final var sunday = requestedQWeek.getStart().minusDays(1L);

    return balanceQuery.getRawByDriverAndDate(driverId, sunday);
  }

  private Long getCallSignId(final Long driverId, final Long qWeekId) {
    final var callSignLink = callSignLinkQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
    if (callSignLink == null) {
      throw new RuntimeException(
          format(
              "No Call-sign-link found for driver.id = %d during week.id = %d", driverId, qWeekId));
    }
    return callSignLink.getCallSignId();
  }

  private Long getCarId(final Long driverId, final Long qWeekId) {
    final var carLink = carLinkQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
    if (carLink == null) {

      return null;
    }
    return carLink.getCarId();
  }

  private WeeklyReportObligationStatus getWeeklyReportObligationStatusForMondayReport(
      final DriverResponse driver, final QWeekResponse qWeek) {
    final var obligation =
        obligationQuery.getByDriverIdAndQWeekIdOnThursday(driver.getId(), qWeek.getId());
    if (obligation.getMatchCount() > 0) {

      return WeeklyReportObligationStatus.COMPLETED;
    }

    return WeeklyReportObligationStatus.NOT_COMPLETED;
  }

  private WeeklyReportTransactionsLink getWeeklyReportTransactions(
      final WeeklyReport weeklyReport, final Long driverId, final Long qWeekId) {
    final var transactionIds =
        transactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
            .map(TransactionResponse::getId)
            .collect(Collectors.toSet());

    return WeeklyReportTransactionsLink.builder()
        .weeklyReport(weeklyReport)
        .transactionIds(transactionIds)
        .build();
  }
}
