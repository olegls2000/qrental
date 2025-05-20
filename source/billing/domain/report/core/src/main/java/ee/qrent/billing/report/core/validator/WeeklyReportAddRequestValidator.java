package ee.qrent.billing.report.core.validator;

import ee.qrent.billing.bonus.api.in.query.GetObligationCalculationQuery;
import ee.qrent.billing.bonus.api.in.query.GetObligationQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportAddRequest;
import ee.qrent.common.in.validation.AddRequestValidator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.invoice.api.in.request.InvoiceAddRequest;
import ee.qrent.billing.invoice.api.out.InvoiceLoadPort;

import ee.qrent.billing.invoice.domain.Invoice;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class WeeklyReportAddRequestValidator
    implements AddRequestValidator<WeeklyReportAddRequest> {

  private final GetObligationCalculationQuery obligationCalculationQuery;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public ViolationsCollector validate(final WeeklyReportAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var latestCalculatedQWeekId = obligationCalculationQuery.getLastCalculatedQWeekId();
    if (request.getQWeekId() == latestCalculatedQWeekId) {

      return violationsCollector;
    }

    final var requestedQWeek = qWeekQuery.getById(request.getQWeekId());
    final var latestCalculatedQWeek = qWeekQuery.getById(latestCalculatedQWeekId);

    if (requestedQWeek.compareTo(latestCalculatedQWeek) > 0) {
      violationsCollector.collect(
          format(
              "Impossible to create Weekly Report for the week: %d - %d. Obligation calculation is missing.",
              requestedQWeek.getYear(), requestedQWeek.getNumber()));
    }

    return violationsCollector;
  }
}
