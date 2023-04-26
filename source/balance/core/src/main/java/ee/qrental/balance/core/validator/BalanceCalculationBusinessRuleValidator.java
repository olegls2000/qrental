package ee.qrental.balance.core.validator;

import static ee.qrental.common.core.utils.QTimeUtils.getLastSundayFromDate;
import static java.time.temporal.ChronoUnit.DAYS;

import ee.qrental.balance.api.out.BalanceCalculationLoadPort;
import ee.qrental.balance.domain.BalanceCalculation;
import ee.qrental.common.core.in.validation.QValidator;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationBusinessRuleValidator implements QValidator<BalanceCalculation> {

  private final BalanceCalculationLoadPort loadPort;

  @Override
  public ViolationsCollector validateAdd(final BalanceCalculation domain) {
    final var violationsCollector = new ViolationsCollector();
    if (isCalculationRequired(domain.getActionDate())) {
      return violationsCollector;
    }
    violationsCollector.collect("No Data for Invoice Calculation.");

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(final BalanceCalculation domain) {
    final var violationsCollector = new ViolationsCollector();
    checkExistence(domain.getId(), violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final BalanceCalculation domain) {
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
