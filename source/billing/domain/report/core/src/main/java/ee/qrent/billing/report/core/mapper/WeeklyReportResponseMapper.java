package ee.qrent.billing.report.core.mapper;

import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.report.api.in.response.WeeklyReportResponse;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportResponseMapper
    implements ResponseMapper<WeeklyReportResponse, WeeklyReport> {

  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetCarLinkQuery carLinkQuery;
  private final GetFirmQuery firmQuery;

  @Override
  public WeeklyReportResponse toResponse(final WeeklyReport domain) {
    final var qWeekId = domain.getQWeekId();
    final var driverId = domain.getDriverId();
    final var qWeek = qWeekQuery.getById(qWeekId);
    final var driver = driverQuery.getById(driverId);
    final var carLink = carLinkQuery.getActiveByDriverIdAndQWeekId(qWeekId, driverId);
    final var qFirm = firmQuery.getById(domain.getQFirmId());

    return WeeklyReportResponse.builder()
        .id(domain.getId())
        .driverName(driver.getFirstName() + " " + driver.getLastName())
        .driverTaxNumber(driver.getTaxNumber())
        .callSign(driver.getCallSign())
        .carRegistrationNumber(carLink.getRegistrationNumber())
        .weekYear(qWeek.getYear())
        .weekNumber(qWeek.getNumber())
        .startDate(qWeek.getStart())
        .endDate(qWeek.getEnd())
        .qFirmName(qFirm.getName())
        .weeksCountTillEnd(domain.getWeeksCountTillEnd())
        .depositObligation(domain.getDepositObligation())
        .depositPaid(domain.getDepositPaid())
        .obligationStatus(domain.getObligationStatus().name())
        .balanceAmountSunday(domain.getBalanceAmountSunday())
        .balanceAmountMonday(domain.getBalanceAmountMonday())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final WeeklyReport domain) {
    // TODO add mapping
    return null;
  }
}
