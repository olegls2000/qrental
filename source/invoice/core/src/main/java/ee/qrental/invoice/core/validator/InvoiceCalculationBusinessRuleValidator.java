package ee.qrental.invoice.core.validator;

import ee.qrent.common.in.validation.QValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.invoice.api.out.InvoiceCalculationLoadPort;
import ee.qrental.invoice.domain.InvoiceCalculation;
import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

import static ee.qrental.common.utils.QTimeUtils.getWeekNumber;

@AllArgsConstructor
public class InvoiceCalculationBusinessRuleValidator implements QValidator<InvoiceCalculation> {

  private final GetQWeekQuery qWeekQuery;
  private final InvoiceCalculationLoadPort loadPort;

  @Override
  public ViolationsCollector validateAdd(final InvoiceCalculation domain) {
    final var violationsCollector = new ViolationsCollector();

    // TODO: make like ObligationCalculationAddBusinessRuleValidator
    // if (isCalculationRequired(domain.getActionDate())) {
    // return violationsCollector;
    // }
    // violationsCollector.collect("No Data for Invoice Calculation.");

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(final InvoiceCalculation domain) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(domain.getId(), violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    return new ViolationsCollector();
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
