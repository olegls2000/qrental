package ee.qrental.transaction.core.validator;

import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import java.time.DayOfWeek;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationAddBusinessRuleValidator {

  public ViolationsCollector validate(final RentCalculationAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();

    final var dayOfWeek = addRequest.getActionDate().getDayOfWeek();
    if (dayOfWeek != DayOfWeek.MONDAY) {
      violationsCollector.collect("Calculation must be started on Monday!");
    }

    return violationsCollector;
  }
}
