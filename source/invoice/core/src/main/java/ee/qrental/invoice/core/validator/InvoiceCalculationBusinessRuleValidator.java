package ee.qrental.invoice.core.validator;

import static ee.qrental.common.core.utils.QTimeUtils.getLastSundayFromDate;
import static java.time.temporal.ChronoUnit.DAYS;

import ee.qrental.common.core.in.validation.QValidator;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.invoice.api.out.InvoiceCalculationLoadPort;
import ee.qrental.invoice.domain.InvoiceCalculation;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationBusinessRuleValidator implements QValidator<InvoiceCalculation> {

  private final InvoiceCalculationLoadPort loadPort;

  @Override
  public ViolationsCollector validateAdd(final InvoiceCalculation domain) {
    final var violationsCollector = new ViolationsCollector();
    if (isCalculationRequired(domain.getActionDate())) {
      return violationsCollector;
    }
    violationsCollector.collect("No Data for Invoice Calculation.");

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
    final var actionDateFormal = getLastSundayFromDate(actionDate);
    final var lastCalculationDate = loadPort.loadLastCalculationDate();
    final long daysBetween = DAYS.between(lastCalculationDate, actionDateFormal);

    return daysBetween >= 7;
  }
}
