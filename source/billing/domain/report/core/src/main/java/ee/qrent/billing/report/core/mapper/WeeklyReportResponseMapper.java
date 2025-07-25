package ee.qrent.billing.report.core.mapper;

import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportTypeIn;
import ee.qrent.billing.report.api.in.response.WeeklyReportResponse;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportType;
import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportResponseMapper
    implements ResponseMapper<WeeklyReportResponse, WeeklyReport> {

  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetCarLinkQuery carLinkQuery;

  @Override
  public WeeklyReportResponse toResponse(final WeeklyReport domain) {
    final var qWeekId = domain.getQWeekId();
    final var driverId = domain.getDriverId();
    final var qWeek = qWeekQuery.getById(qWeekId);
    final var driver = driverQuery.getById(driverId);
    final var carLink = carLinkQuery.getActiveByDriverIdAndQWeekId(driverId, qWeekId);

    return WeeklyReportResponse.builder()
        .id(domain.getId())
        .type(getInType(domain.getType()).getLabel())
        .driverName(driver.getFirstName() + " " + driver.getLastName())
        .driverTaxNumber(driver.getTaxNumber())
        .callSign(driver.getCallSign())
        .carRegistrationNumber(
            carLink == null ? "No Car during period" : carLink.getRegistrationNumber())
        .weekYear(qWeek.getYear())
        .weekNumber(qWeek.getNumber())
        .startDate(qWeek.getStart())
        .endDate(qWeek.getEnd())
        .weeksCountTillEnd(domain.getWeeksCountTillEnd())
        .depositObligation(domain.getDepositObligation())
        .depositPaid(domain.getDepositPaid())
        .obligationStatus(domain.getObligationStatus().name())
        .balanceAmountSunday(domain.getBalanceAmountSunday())
        .balanceAmountAtCalculationMoment(domain.getBalanceAmountAtCalculationMoment())
        .comment(domain.getComment())
        .build();
  }

  private WeeklyReportTypeIn getInType(final WeeklyReportType type) {

    return WeeklyReportTypeIn.valueOf(type.name());
  }

  @Override
  public String toObjectInfo(final WeeklyReport domain) {
    // TODO add mapping
    return null;
  }
}
