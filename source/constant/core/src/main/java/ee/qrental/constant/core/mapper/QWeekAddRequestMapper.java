package ee.qrental.constant.core.mapper;


import static ee.qrental.common.utils.QTimeUtils.*;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.constant.api.in.request.QWeekAddRequest;
import ee.qrental.constant.domain.QWeek;

public class QWeekAddRequestMapper implements AddRequestMapper<QWeekAddRequest, QWeek> {

  private static final String WEEK_PATTERN = "dd-MMM";

  @Override
  public QWeek toDomain(final QWeekAddRequest request) {
    final var actionDate = request.getWeekDate();
    final var weekNumber = getWeekNumber(actionDate);
    final var year = actionDate.getYear();
    final var description = getDescription(year, weekNumber);

    return QWeek.builder().id(null).number(weekNumber).year(year).description(description).build();
  }

  private String getDescription(final Integer year, final Integer weekNumber) {
    final var start = getFirstDayOfWeekInYear(year, weekNumber);
    final var startDescription = ofPattern(WEEK_PATTERN).format(start);
    final var end = getLastDayOfWeekInYear(year, weekNumber);
    final var endDescription = ofPattern(WEEK_PATTERN).format(end);
    final var description = format("%s ... %s", startDescription, endDescription);

    return description;
  }
}
