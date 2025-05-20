package ee.qrent.billing.report.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;
import static java.lang.String.format;

import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportAddRequest;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportAddUseCase;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportDetail;
import ee.qrent.billing.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;

import ee.qrent.billing.report.core.mapper.WeeklyReportAddRequestMapper;
import ee.qrent.billing.report.core.mapper.InvoiceUpdateRequestMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class WeeklyReportUseCaseService implements WeeklyReportAddUseCase {

  /*  private final InvoiceAddPort addPort;
  private final InvoiceUpdatePort updatePort;
  private final InvoiceDeletePort deletePort;
  private final InvoiceLoadPort loadPort;*/

  private final QDateTime qDateTime;
  private final GetObligationQuery obligationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetCarLinkQuery carLink;
  private final GetBalanceQuery balanceQuery;
  private final WeeklyReportAddRequestMapper addRequestMapper;
  private final InvoiceUpdateRequestMapper updateRequestMapper;
  private final AddRequestValidator<WeeklyReportAddRequest> addRequestValidator;

  @Override
  public Long add(final WeeklyReportAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var requestedQWeek = qWeekQuery.getById(request.getQWeekId());

    driverQuery.getAll().stream()
        .forEach(
            driver -> {
              final var callSignLink =
                  callSignLinkQuery.getCallSignLinkByDriverIdAndDate(
                      driver.getId(), requestedQWeek.getEnd());
              final var callSign = callSignLink.getCallSign();

              final var activeCarLink =
                  carLink.getActiveByQWeekIdAndDriverId(request.getQWeekId(), driver.getId());
              final var carRegistrationNumber = activeCarLink.getRegistrationNumber();

              final var balance =
                  balanceQuery.getByDriverIdAndQWeekId(driver.getId(), request.getQWeekId());
              final var balanceAmount = balance.getAmount();

              final var weeklyReportDomain =
                  WeeklyReport.builder()
                      .id(null)
                      .qWeekId(request.getQWeekId())
                      .driverId(driver.getId())
                      .driverName(format("%s %s", driver.getLastName(), driver.getFirstName()))
                      .driverTaxNumber(driver.getTaxNumber())
                      .callSign(callSign)
                      .carRegistrationNumber(carRegistrationNumber)
                      .weekYear(requestedQWeek.getYear())
                      .weekNumber(requestedQWeek.getNumber())
                      .startDate(requestedQWeek.getStart())
                      .endDate(requestedQWeek.getEnd())
                      .qFirmId(driver.getQFirmId())
                      .qFirmName(driver.getQFirmName())
                      .deposit(driver.getDeposit())
                      .paidDeposit(BigDecimal.valueOf(99999999))
                      // TODO ..
                      .status(null)
                      .balanceAmount(balanceAmount)
                      .build();
            });
  }

  private WeeklyReportDetail getWeeklyReportDetail(final Long qWeekId, final Long driverId) {
    final var obligation =  obligationQuery.getByQWeekIdAndDriverId(qWeekId, driverId);

    return WeeklyReportDetail.builder()
            .obligationTotal(obligation.getAmount())
            .obligationRent(?)


            .build();
  }
}
