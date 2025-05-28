package ee.qrent.billing.report.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;
import static java.lang.String.format;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.query.GetFirmLinkQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportCalculationAddUseCase;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationAddPort;
import ee.qrent.billing.report.core.mapper.WeeklyReportCalculationAddRequestMapper;
import ee.qrent.billing.report.core.validator.WeeklyReportCalculationAddRequestValidator;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.billing.report.domain.WeeklyReportObligationStatus;
import ee.qrent.billing.report.domain.WeeklyReportTransactionsLink;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceResponse;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class WeeklyReportCalculationUseCaseService implements WeeklyReportCalculationAddUseCase {

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
  private final GetTransactionQuery getTransactionQuery;

  @Transactional
  @Override
  public Long add(final WeeklyReportCalculationAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var requestedQWeekId = request.getQWeekId();
    final var domain = addRequestMapper.toDomain(request);
    final var requestedQWeek = qWeekQuery.getById(requestedQWeekId);

    driverQuery.getAll().stream()
        .forEach(
            driver -> {
              final var weeklyReport = getWeeklyReport(driver, requestedQWeek);
              final var reportTransactions =
                  getWeeklyReportTransactions(weeklyReport, driver.getId(), requestedQWeekId);
              domain.getReportTransactionLinks().add(reportTransactions);
            });

    final var addedDomain = addPort.add(domain);
    sendEmailNotification(addedDomain);

    return addedDomain.getId();
  }

  private WeeklyReport getWeeklyReport(
      final DriverResponse driver, final QWeekResponse requestedQWeek) {
    final var driverId = driver.getId();
    final var qWeekId = requestedQWeek.getId();
    final var balance = balanceQuery.getByDriverIdAndQWeekId(driverId, qWeekId);
    final var obligation = obligationQuery.getByDriverIdAndQWeekId(driverId, qWeekId);

    return WeeklyReport.builder()
        .qWeekId(qWeekId)
        .driverId(driverId)
        .callSignId(getCallSignId(driverId, qWeekId))
        .carId(getCarId(driverId, qWeekId))
        .qFirmId(getQFirmId(driverId, qWeekId))
        .startDate(requestedQWeek.getStart())
        .endDate(requestedQWeek.getEnd())
        .deposit(driver.getDeposit())
        .paidDeposit(BigDecimal.valueOf(999))
        .status(getWeeklyReportObligationStatus(driverId, qWeekId, balance))
        .balanceAmount(balance.getAmount())
        .obligationTotal(obligation == null ? BigDecimal.valueOf(999) : obligation.getAmount())
        .obligationRent(BigDecimal.valueOf(999))
        .obligationDebt(BigDecimal.valueOf(999))
        .obligationRepairment(BigDecimal.valueOf(999))
        .obligationRepairmentFranchise(BigDecimal.valueOf(999))
        .obligationOthers(BigDecimal.valueOf(999))
        .obligationFee(BigDecimal.valueOf(999))
        .bonusNewDriver(BigDecimal.valueOf(999))
        .bonusReliablePartner(BigDecimal.valueOf(999))
        .bonusBolt(BigDecimal.valueOf(999))
        .bonusFriend(BigDecimal.valueOf(999))
        .obligationRentAdjustmentBolt(BigDecimal.valueOf(999))
        .obligationRentAdjustmentForus(BigDecimal.valueOf(999))
        .prepayment(BigDecimal.valueOf(999))
        .comment("Automatically generated weekly report")
        .build();
  }

  private Long getCallSignId(final Long driverId, final Long qWeekId) {
    final var callSignLink = callSignLinkQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
    if (callSignLink == null) {
      return 11111L;
      /* throw new RuntimeException(
      format(
          "No Call-sign-link found for driver.id = %d during week.id = %d", driverId, qWeekId));*/
    }
    return callSignLink.getCallSignId();
  }

  private Long getCarId(final Long driverId, final Long qWeekId) {
    final var carLink = carLinkQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
    if (carLink == null) {
      return 11111L;
      /* throw new RuntimeException(
      format("No Car-link found for driver.id = %d during week.id = %d", driverId, qWeekId));*/
    }
    return carLink.getCarId();
  }

  private Long getQFirmId(final Long driverId, final Long qWeekId) {
    final var firmLink = firmLinkQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);
    if (firmLink == null) {
      return 11111L;
      /* throw new RuntimeException(
      format("No QFirm-link found for driver.id = %d during week.id = %d", driverId, qWeekId));*/
    }
    return firmLink.getFirmId();
  }

  private WeeklyReportObligationStatus getWeeklyReportObligationStatus(
      final Long driverId, final Long qWeekId, final BalanceResponse balance) {
    final var obligation = obligationQuery.getByDriverIdAndQWeekId(driverId, qWeekId);
    /*    if (obligation == null) {
      throw new RuntimeException(
          format(
              "Obligation  for the driver.id: %d and week.id: %d does not exist. Please calculate it first.",
              driverId, qWeekId));
    }*/

    if (obligation == null || obligation.getMatchCount() > 0) {

      return WeeklyReportObligationStatus.COMPLETED;
    }

    if (balance == null) {
      return WeeklyReportObligationStatus.NOT_COMPLETED;
    }

    if (balance.getAmount().compareTo(BigDecimal.ZERO) > 0) {

      return WeeklyReportObligationStatus.COMPLETED_WITH_DELAY;
    }

    return WeeklyReportObligationStatus.NOT_COMPLETED;
  }

  private WeeklyReportTransactionsLink getWeeklyReportTransactions(
      final WeeklyReport weeklyReport, final Long driverId, final Long qWeekId) {
    final var transactionIds =
        getTransactionQuery.getAllByDriverIdAndQWeekId(driverId, qWeekId).stream()
            .map(TransactionResponse::getId)
            .collect(Collectors.toSet());

    return WeeklyReportTransactionsLink.builder()
        .weeklyReport(weeklyReport)
        .transactionIds(transactionIds)
        .build();
  }

  private void sendEmailNotification(WeeklyReportCalculation calculation) {
    // TODO add mail logic here
  }
}
