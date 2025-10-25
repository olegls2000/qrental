package ee.qrent.billing.bonus.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.billing.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrent.billing.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;

import java.time.DayOfWeek;
import java.util.Objects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculationAddRequestValidator
    implements AddRequestValidator<ObligationCalculationAddRequest> {

  private final GetQWeekQuery qWeekQuery;
  private final ObligationCalculationLoadPort loadPort;

  @Override
  public ViolationsCollector validate(final ObligationCalculationAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    //checkIfActionDateTuesday(addRequest, violationsCollector);
    checkIfPreviousWeekHasCalculatedObligation(addRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfActionDateTuesday(
      final ObligationCalculationAddRequest addRequest,
      final ViolationsCollector violationsCollector) {
    final var requestedDayOfWeek = addRequest.getActionDate().getDayOfWeek();
    if (requestedDayOfWeek != DayOfWeek.THURSDAY) {
      final var violation = "Obligation Calculation can be started only on Thursday.";
      System.out.println(violation);
      violationsCollector.collect(violation);
    }
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
    if (latestCalculatedQWeekId == null) {
      System.out.println(
          "No Obligation Calculation were done yet, current calculation will be the first.");
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
