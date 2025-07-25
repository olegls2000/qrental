package ee.qrent.billing.report.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;
import static java.lang.String.format;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.deposit.api.in.query.GetDepositQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.query.GetFirmLinkQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.api.in.request.WeeklyReportSendByEmailRequest;
import ee.qrent.billing.report.api.in.request.WeeklyReportTypeIn;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportCalculationAddUseCase;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportSendByEmailUseCase;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationAddPort;
import ee.qrent.billing.report.core.mapper.WeeklyReportCalculationAddRequestMapper;
import ee.qrent.billing.report.core.validator.WeeklyReportCalculationAddRequestValidator;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportObligationStatus;
import ee.qrent.billing.report.domain.WeeklyReportTransactionsLink;
import ee.qrent.billing.report.domain.WeeklyReportType;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

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
  private final GetFirmLinkQuery firmLinkQuery;
  private final GetBalanceQuery balanceQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetContractQuery contractQuery;
  private final GetDepositQuery depositQuery;
  private final WeeklyReportSendByEmailUseCase sendByEmailUseCase;

  @Transactional
  @Override
  public Long add(final WeeklyReportCalculationAddRequest request) {

    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var requestedQWeekId = request.getQWeekId();
    final var calculation = addRequestMapper.toDomain(request);
    final var requestedQWeek = qWeekQuery.getById(requestedQWeekId);

    driverQuery.getAll().parallelStream()
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

    final var addedReportCalculation = addPort.add(calculation);

    addedReportCalculation
        .getReportTransactionLinks()
        .forEach(
            link -> {
              final var reportId = link.getWeeklyReport().getId();
              final var emailSendRequest = new WeeklyReportSendByEmailRequest(reportId);
              sendByEmailUseCase.sendByEmail(emailSendRequest);
            });

    return addedReportCalculation.getId();
  }

  private WeeklyReport getWeeklyReport(
      final DriverResponse driver,
      final QWeekResponse requestedQWeek,
      final WeeklyReportTypeIn reportType) {
    final var driverId = driver.getId();
    final var qWeekId = requestedQWeek.getId();
    final var obligation = obligationQuery.getByDriverIdAndQWeekId(driverId, qWeekId);
    final var contract = contractQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
    final var depositPaid = depositQuery.getPaidAmountByDriverId(driverId);

    final var balanceAmountOnDate = getBalanceAmountOnDate(requestedQWeek, reportType, driverId);

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
        .obligationStatus(getWeeklyReportObligationStatus(driver, requestedQWeek))
        .balanceAmountSunday(balanceAmountOnDate)
        .balanceAmountAtCalculationMoment(balanceAmountOnDate)
        .comment("Automatically generated weekly report")
        .build();
  }

  private BigDecimal getBalanceAmountOnDate(
      final QWeekResponse requestedQWeek,
      final WeeklyReportTypeIn reportType,
      final Long driverId) {
    LocalDate reportDate = null;
    switch (reportType) {
      case MONDAY_REPORT -> reportDate = requestedQWeek.getStart();
      case TUESDAY_REPORT -> reportDate = requestedQWeek.getStart().plusDays(1l);
      case WEDNESDAY_REPORT -> reportDate = requestedQWeek.getStart().plusDays(2l);
      case FRIDAY_REPORT -> reportDate = requestedQWeek.getStart().plusDays(4l);
    }
    final var rawBalanceOnReportDate = balanceQuery.getRawByDriverAndDate(driverId, reportDate);

    return rawBalanceOnReportDate.getAmount();
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
      /*      throw new RuntimeException(
      format("No Car-link found for driver.id = %d during week.id = %d", driverId, qWeekId));*/
      return null;
    }
    return carLink.getCarId();
  }

  private Long getQFirmId(final Long driverId, final Long qWeekId) {
    final var firmLink = firmLinkQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
    if (firmLink == null) {
      throw new RuntimeException(
          format("No QFirm-link found for driver.id = %d during week.id = %d", driverId, qWeekId));
    }
    return firmLink.getFirmId();
  }

  private WeeklyReportObligationStatus getWeeklyReportObligationStatus(
      final DriverResponse driver, final QWeekResponse qWeek) {
    final var obligation = obligationQuery.getByDriverIdAndQWeekId(driver.getId(), qWeek.getId());
    if (obligation == null) {
      System.out.println("Driver id without Obligation:" + driver.getId());
      return WeeklyReportObligationStatus.NOT_COMPLETED;

      /*      throw new RuntimeException(
      format(
          "Obligation  for the Driver: %s %s, tax number: %d and week: %d - %d does not exist. Please calculate it first.",
          driver.getFirstName(),
          driver.getLastName(),
          driver.getTaxNumber(),
          qWeek.getYear(),
          qWeek.getNumber()));*/
    }

    final var wednesday = qWeek.getEnd().plusDays(3l);
    final var balanceOnWednesday = balanceQuery.getRawByDriverAndDate(driver.getId(), wednesday);

    if (obligation.getMatchCount() == 0
        && balanceOnWednesday.getAmount().compareTo(BigDecimal.ZERO) >= 0) {
      return WeeklyReportObligationStatus.COMPLETED_WITH_DELAY;
    }

    if (obligation.getMatchCount() == 0
        && balanceOnWednesday.getAmount().compareTo(BigDecimal.ZERO) < 0) {
      return WeeklyReportObligationStatus.NOT_COMPLETED;
    }
    // in case of match count >0
    return WeeklyReportObligationStatus.COMPLETED;
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
