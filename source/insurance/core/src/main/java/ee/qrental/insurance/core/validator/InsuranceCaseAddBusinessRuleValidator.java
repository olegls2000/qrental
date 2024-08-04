package ee.qrental.insurance.core.validator;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.insurance.api.in.request.InsuranceCaseAddRequest;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseAddBusinessRuleValidator {

  private final InsuranceCaseLoadPort loadPort;
  private final QDateTime qDateTime;

  public ViolationsCollector validate(final InsuranceCaseAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    final var occurrenceDate = request.getOccurrenceDate();
    if (occurrenceDate.isBefore(qDateTime.getToday())) {
      violationsCollector.collect("Occurrence date must be after current date");
    }

    return violationsCollector;
  }
}
