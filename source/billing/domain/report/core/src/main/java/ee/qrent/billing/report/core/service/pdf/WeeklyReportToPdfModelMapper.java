package ee.qrent.billing.report.core.service.pdf;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.domain.WeeklyReport;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportToPdfModelMapper {

    private final GetQWeekQuery qWeekQuery;
    private final GetDriverQuery driverQuery;
    private final GetCallSignQuery callSignQuery;

    public WeeklyReportPdfModel getPdfModel(final WeeklyReport report) {
        final var driver = driverQuery.getById(report.getDriverId());
        final var callSign = callSignQuery.getById(report.getCallSignId());
        final var previousWeek = qWeekQuery.getById(report.getQWeekId());
        final var currentWeek = qWeekQuery.getOneAfterById(report.getQWeekId());

        return WeeklyReportPdfModel.builder()
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .taxNumber(driver.getTaxNumber())
                .callSign(callSign.getCallSign())
                .previousWeekStart(previousWeek.getStart())
                .previousWeekEnd(previousWeek.getEnd())
                .currentWeekStart(currentWeek.getStart())
                .currentWeekEnd(currentWeek.getEnd())
                .feeAmountSunday(report.getFeeAmountSunday())

                // TODO add mapping
                .build();
    }
}
