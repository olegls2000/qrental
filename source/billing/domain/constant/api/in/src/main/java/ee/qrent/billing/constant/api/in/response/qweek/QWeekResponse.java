package ee.qrent.billing.constant.api.in.response.qweek;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.String.format;

@SuperBuilder
@Getter
public class QWeekResponse implements Comparable<QWeekResponse> {
  private Long id;
  private Integer year;
  private Integer number;
  private LocalDate start;
  private LocalDate end;
  private String description;

  @Override
  public String toString() {
    final var formatter = DateTimeFormatter.ofPattern("dd MMM");
    return format(
        "%d-%d (%s ... %s)", year, number, start.format(formatter), end.format(formatter));
  }

  @Override
  public int compareTo(final QWeekResponse otherQWeek) {
    final var yearDiff = this.year - otherQWeek.getYear();
    if (yearDiff != 0) {
      return yearDiff;
    }
    return this.number - otherQWeek.getNumber();
  }
}
