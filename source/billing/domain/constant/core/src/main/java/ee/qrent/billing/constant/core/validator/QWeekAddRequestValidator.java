package ee.qrent.billing.constant.core.validator;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.constant.api.in.request.QWeekAddRequest;
import ee.qrent.billing.constant.api.out.QWeekLoadPort;
import lombok.AllArgsConstructor;
import org.threeten.extra.YearWeek;

import static java.lang.String.format;

@AllArgsConstructor
public class QWeekAddRequestValidator implements AddRequestValidator<QWeekAddRequest> {
  private final QWeekLoadPort loadPort;

  public ViolationsCollector validate(final QWeekAddRequest addRequest) {
    final var violationsCollector = new ViolationsCollector();
    checkIfWeekForDateAlreadyCreated(addRequest, violationsCollector);

    return violationsCollector;
  }

  private void checkIfWeekForDateAlreadyCreated(
      final QWeekAddRequest addRequest, final ViolationsCollector violationCollector) {
    final var weekDate = addRequest.getWeekDate();
    final var yearWeek = YearWeek.from(weekDate);
    final var year = yearWeek.getYear();
    final var weekNumber = yearWeek.getWeek();

    final var weekFromDb = loadPort.loadByYearAndNumber(year, weekNumber);

    if (weekFromDb != null) {
      violationCollector.collect(format("Week: %d already exists.", weekNumber));
    }
  }
}
