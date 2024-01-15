package ee.qrental.bonus.core.validator;

import static java.lang.String.format;

import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrental.bonus.api.out.ObligationLoadPort;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculationAddBusinessRuleValidator {

  private final GetQWeekQuery qWeekQuery;
  private final ObligationLoadPort loadPort;

  public ViolationsCollector validate(final ObligationCalculationAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();

    checkIfPreviousWeekHasCalculatedObligation(addRequest, violationsCollector);

    return null;
  }

  private void checkIfPreviousWeekHasCalculatedObligation(
      final ObligationCalculationAddRequest addRequest,
      final ViolationsCollector violationsCollector) {
    final var requestedWeekId = addRequest.getQWeekId();
    final var requestedWeekId = addRequest.get;
    final var previousWeek = qWeekQuery.getOneBeforeById(requestedWeekId);
    if (previousWeek == null) {
      System.out.println("First Obligation Calculation");
      return;
    }
    final var latestCalculatedQWeekId = loadPort.loadByDriverIdAndByQWeekId();
    if (previousWeek.getId() != latestCalculatedQWeekId) {
      final var violation =
          format(
              "Rent Calculation for previous week - %d was not calculated.",
              previousWeek.getNumber());
      System.out.println(violation);
      violationsCollector.collect(violation);
    }
  }
}
