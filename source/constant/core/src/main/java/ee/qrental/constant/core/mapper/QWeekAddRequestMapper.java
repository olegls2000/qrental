package ee.qrental.constant.core.mapper;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.constant.api.in.request.qweek.QWeekAddRequest;
import ee.qrental.constant.domain.QWeek;

import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

public class QWeekAddRequestMapper implements AddRequestMapper<QWeekAddRequest, QWeek> {

  private static final String WEEK_PATTERN = "dd-MMM";

  @Override
  public QWeek toDomain(final QWeekAddRequest request) {
    final var actionDate = request.getActionDate();

    final var weekNumber = QTimeUtils.getWeekNumber(actionDate);
    final var year = actionDate.getYear();
    final var start = QTimeUtils.getFirstDayOfWeekInYear(year, weekNumber);
    final var end = QTimeUtils.getLastDayOfWeekInYear(year, weekNumber);
    final var startDescription = ofPattern(WEEK_PATTERN).format(start);

    return QWeek.builder()
        .id(null)
        .number(weekNumber)
        .year(year)
        .description(request.getDescription())
        .negative(request.getNegative())
        .build();
  }
}
