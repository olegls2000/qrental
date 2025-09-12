package ee.qrent.billing.report.core.service.pdf;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.report.domain.WeeklyReport;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportToPdfModelMapper {

  private final GetQWeekQuery qWeekQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignQuery callSignQuery;
  private final GetCarQuery carQuery;

  public WeeklyReportPdfModel getPdfModel(final WeeklyReport report) {
    final var driver = driverQuery.getById(report.getDriverId());
    final var callSign = callSignQuery.getById(report.getCallSignId());
    final var previousWeek = qWeekQuery.getById(report.getQWeekId());
    final var currentWeek = qWeekQuery.getOneAfterById(report.getQWeekId());
    final var carReg =
        report.getCarId() != null ? carQuery.getById(report.getCarId()).getRegNumber() : null;

    return WeeklyReportPdfModel.builder()
        .firstName(driver.getFirstName())
        .lastName(driver.getLastName())
        .idNumber(driver.getTaxNumber())
        .callSign(callSign.getCallSign())
        .previousWeekStart(previousWeek.getStart())
        .previousWeekEnd(previousWeek.getEnd())
        .currentWeekStart(currentWeek.getStart())
        .currentWeekEnd(currentWeek.getEnd())
        .feeAmountSunday(report.getFeeAmountSunday())
        .carRegistrationNumber(carReg)
        .depositObligation(report.getDepositObligation())
        .depositPaid(report.getDepositPaid())
        .balanceAmountSunday(report.getBalanceAmountSunday())
        .balanceAmountAtCalculationMoment(report.getBalanceAmountAtCalculationMoment())
        .weeksCountTillEnd(report.getWeeksCountTillEnd())
        .obligationStatus(
            report.getObligationStatus() != null ? report.getObligationStatus().name() : null)
        .transactionTypesVsAmount(report.getTransactionTypesVsAmount())
        .comment(report.getComment())
        // amount: prefer balance at calculation moment, fallback to balance on Sunday
        .amount(
            report.getBalanceAmountAtCalculationMoment() != null
                ? report.getBalanceAmountAtCalculationMoment()
                : report.getBalanceAmountSunday())
        .build();
  }
}
