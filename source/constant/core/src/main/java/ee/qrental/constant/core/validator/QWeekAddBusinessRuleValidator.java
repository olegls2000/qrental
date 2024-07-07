package ee.qrental.constant.core.validator;

import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.common.utils.QTimeUtils;
import ee.qrental.constant.api.in.request.QWeekAddRequest;
import ee.qrental.constant.api.out.QWeekLoadPort;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class QWeekAddBusinessRuleValidator {
  private final QWeekLoadPort loadPort;

  public ViolationsCollector validate(final QWeekAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfWeekForDateAlreadyCreated(addRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfWeekForDateAlreadyCreated(
      final QWeekAddRequest addRequest, final ViolationsCollector violationCollector) {
    final var weekDate = addRequest.getWeekDate();
    final var year = weekDate.getYear();
    final var weekNumber = QTimeUtils.getWeekNumber(weekDate);

    final var weekFromDb = loadPort.loadByYearAndNumber(year, weekNumber);

    if (weekFromDb != null) {
      violationCollector.collect(format("Week: %d already exists.", weekNumber));
    }
  }
}
