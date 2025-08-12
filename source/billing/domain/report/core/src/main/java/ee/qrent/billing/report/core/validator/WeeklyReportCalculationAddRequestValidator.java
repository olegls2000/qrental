package ee.qrent.billing.report.core.validator;

import ee.qrent.billing.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportCalculationAddRequest;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationLoadPort;
import ee.qrent.billing.report.domain.WeeklyReportType;
import ee.qrent.common.in.validation.AddRequestValidator;

import ee.qrent.common.in.validation.ViolationsCollector;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class WeeklyReportCalculationAddRequestValidator
    implements AddRequestValidator<WeeklyReportCalculationAddRequest> {

  private final GetObligationCalculationQuery obligationCalculationQuery;
  private final GetQWeekQuery qWeekQuery;
  private final WeeklyReportCalculationLoadPort loadPort;

  @Override
  public ViolationsCollector validate(final WeeklyReportCalculationAddRequest request) {
    final var violationsCollector = new ViolationsCollector();

   /* final var duplicate =
        loadPort.loadByQWeekIdAndReportType(
            request.getQWeekId(), WeeklyReportType.valueOf(request.getType().name()));

    if (duplicate != null) {
      violationsCollector.collect(
          format(
              "Weekly Report for the week.id: %d  and type: %s already exists.",
              request.getQWeekId(), request.getType().name()));
    }*/
    final var previousWeek = qWeekQuery.getOneBeforeById(request.getQWeekId());
    final var latestCalculatedQWeekId = obligationCalculationQuery.getLastCalculatedQWeekId();
    if (previousWeek.getId() == latestCalculatedQWeekId) {

      return violationsCollector;
    }


    final var latestCalculatedQWeek = qWeekQuery.getById(latestCalculatedQWeekId);

    if (previousWeek.compareTo(latestCalculatedQWeek) > 0) {
      violationsCollector.collect(
          format(
              "Impossible to create Weekly Report for the week: %d - %d. Obligation calculation is missing.",
                  previousWeek.getYear(), previousWeek.getNumber()));
    }

    return violationsCollector;
  }
}
