package ee.qrental.transaction.core.validator;


import static ee.qrental.common.utils.QTimeUtils.getLastSundayFromDate;
import static java.time.temporal.ChronoUnit.DAYS;

import ee.qrent.common.in.validation.QValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.transaction.api.out.balance.BalanceCalculationLoadPort;
import ee.qrental.transaction.domain.balance.BalanceCalculation;
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
    violationsCollector.collect("No Data for Balance Calculation.");

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(final BalanceCalculation domain) {
    final var violationsCollector = new ViolationsCollector();
    System.err.println("Update is not implemented, check the call logic");

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    return new ViolationsCollector();
  }

  public boolean isCalculationRequired(final LocalDate actionDate) {
    final var actionDateFormal = getLastSundayFromDate(actionDate);
    final var lastCalculationDate = loadPort.loadLastCalculatedDate();
    final long daysBetween = DAYS.between(lastCalculationDate, actionDateFormal);

    return daysBetween >= 7;
  }
}
