package ee.qrent.billing.invoice.core.validator;

import ee.qrent.common.in.validation.AddRequestValidator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrent.billing.invoice.api.out.InvoiceCalculationLoadPort;
import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

import static ee.qrent.common.utils.QTimeUtils.getWeekNumber;

@AllArgsConstructor
public class InvoiceCalculationAddRequestValidator
    implements AddRequestValidator<InvoiceCalculationAddRequest> {

  private final GetQWeekQuery qWeekQuery;
  private final InvoiceCalculationLoadPort loadPort;

  @Override
  public ViolationsCollector validate(final InvoiceCalculationAddRequest request) {
    final var violationsCollector = new ViolationsCollector();

    // TODO: make like ObligationCalculationAddBusinessRuleValidator
    // if (isCalculationRequired(domain.getActionDate())) {
    // return violationsCollector;
    // }
    // violationsCollector.collect("No Data for Invoice Calculation.");

    return violationsCollector;
  }

  private void checkExistence(final Long id, final ViolationsCollector violationsCollector) {
    if (loadPort.loadById(id) == null) {
      violationsCollector.collect("Update of Invoice failed. No Record with id = " + id);
    }
  }

  public boolean isCalculationRequired(final LocalDate actionDate) {
    if (actionDate == null) {
      throw new RuntimeException("Impossible to calculate last Sunday, if fromDate is NULL.");
    }
    if (actionDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
      return false;
    }
    final var actionDateYear = actionDate.getYear();
    final var actionDateWeekNumber = getWeekNumber(actionDate);
    final var actionDateQWeek = qWeekQuery.getByYearAndNumber(actionDateYear, actionDateWeekNumber);
    if (actionDateQWeek == null) {
      throw new RuntimeException(
          "Q-Week  for " + actionDateYear + " does not exist, please create a QWeek in Settings");
    }
    final var previousQWek = qWeekQuery.getOneBeforeById(actionDateQWeek.getId());
    final var actionDateFormal = previousQWek.getEnd();
    // final var lastCalculationDate = loadPort.loadLastCalculatedDate();
    // final long daysBetween = DAYS.between(lastCalculationDate, actionDateFormal);

    // return daysBetween >= 7;
    return true;
  }
}
