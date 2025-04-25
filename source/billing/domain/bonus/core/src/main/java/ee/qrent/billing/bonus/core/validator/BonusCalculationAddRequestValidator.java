package ee.qrent.billing.bonus.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.bonus.api.in.request.BonusCalculationAddRequest;
import ee.qrent.billing.bonus.api.out.BonusCalculationLoadPort;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import java.util.Objects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusCalculationAddRequestValidator implements AddRequestValidator<BonusCalculationAddRequest> {

  private final GetQWeekQuery qWeekQuery;
  private final BonusCalculationLoadPort loadPort;

  public ViolationsCollector validate(final BonusCalculationAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfPreviousWeekHasCalculatedObligation(addRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfPreviousWeekHasCalculatedObligation(
      final BonusCalculationAddRequest addRequest,
      final ViolationsCollector violationsCollector) {
    final var requestedWeekId = addRequest.getQWeekId();
    final var previousWeek = qWeekQuery.getOneBeforeById(requestedWeekId);
    if (previousWeek == null) {
      System.out.println("First Bonus Calculation");
      return;
    }
    final var latestCalculatedQWeekId = loadPort.loadLastCalculatedQWeekId();
    if(latestCalculatedQWeekId == null){
      System.out.println("No Bonus Calculation were done yet, current calculation will be the first.");
      return;
    }
    if (!Objects.equals(previousWeek.getId(), latestCalculatedQWeekId)) {
      final var violation =
          format(
              "Bonus Calculation for previous week - %d was not calculated.",
              previousWeek.getNumber());
      System.out.println(violation);
      violationsCollector.collect(violation);
    }
  }
}
