package ee.qrental.bonus.core.validator;

import static java.lang.String.format;

import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrental.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class ObligationCalculationAddBusinessRuleValidator {

  private final GetQWeekQuery qWeekQuery;
  private final ObligationCalculationLoadPort loadPort;

  public ViolationsCollector validate(final ObligationCalculationAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfPreviousWeekHasCalculatedObligation(addRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfPreviousWeekHasCalculatedObligation(
      final ObligationCalculationAddRequest addRequest,
      final ViolationsCollector violationsCollector) {
    final var requestedWeekId = addRequest.getQWeekId();
    final var previousWeek = qWeekQuery.getOneBeforeById(requestedWeekId);
    if (previousWeek == null) {
      System.out.println("First Obligation Calculation");
      return;
    }
    final var latestCalculatedQWeekId = loadPort.loadLastCalculatedQWeekId();
    if(latestCalculatedQWeekId == null){
      System.out.println("No Obligation Calculation were done yet, current calculation will be the first.");
      return;
    }
    if (!Objects.equals(previousWeek.getId(), latestCalculatedQWeekId)) {
      final var violation =
          format(
              "Obligation Calculation for previous week - %d was not calculated.",
              previousWeek.getNumber());
      System.out.println(violation);
      violationsCollector.collect(violation);
    }
  }
}
