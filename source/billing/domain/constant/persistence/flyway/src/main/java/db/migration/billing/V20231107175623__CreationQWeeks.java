package db.migration.billing;

import ee.qrent.common.utils.QTimeUtils;
import ee.qrent.common.utils.QWeekIterator;
import ee.qrent.common.utils.Week;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.time.LocalDate;
import java.time.Month;

import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;

public class V20231107175623__CreationQWeeks extends BaseJavaMigration {

  private static final String WEEK_PATTERN = "dd-MMM";

  @Override
  public void migrate(final Context context) throws Exception {
    final var startDate = LocalDate.of(2023, Month.JANUARY, 2);
    final var endDate = LocalDate.now();
    final var weekIterator = new QWeekIterator(startDate, endDate);

    try (var statement = context.getConnection().createStatement()) {
      while (weekIterator.hasNext()) {
        final var week = weekIterator.next();
        final var insertQWeekSql = getInsertSql(week);
        final var result = statement.executeUpdate(insertQWeekSql);
        if (result > 0) {
          System.out.println(insertQWeekSql + " -> successfully inserted");
        } else {
          System.out.println(insertQWeekSql + " -> unsuccessfully executed");
        }
      }
    }
  }

  private String getInsertSql(final Week week) {
    final var year = week.year();
    final var weekNumber = week.weekNumber();
    final var start = QTimeUtils.getFirstDayOfWeekInYear(year, weekNumber);
    final var startDescription = ofPattern(WEEK_PATTERN).format(start);
    final var end = QTimeUtils.getLastDayOfWeekInYear(year, weekNumber);
    final var endDescription = ofPattern(WEEK_PATTERN).format(end);
    final var description = format("%s ... %s", startDescription, endDescription);

    return format(
        "insert into q_week (year, number, description) values (%d, %d, '%s')",
        year, weekNumber, description);
  }
}
