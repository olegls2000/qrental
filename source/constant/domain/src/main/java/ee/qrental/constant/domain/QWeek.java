package ee.qrental.constant.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import static ee.qrental.common.utils.QTimeUtils.getFirstDayOfWeekInYear;
import static ee.qrental.common.utils.QTimeUtils.getLastDayOfWeekInYear;

@SuperBuilder
@Getter
public class QWeek {
  private Long id;
  private Integer year;
  private Integer number;
  private String description;

  public LocalDate getStart() {
    return getFirstDayOfWeekInYear(year, number);
  }

  public LocalDate getEnd() {
    return getLastDayOfWeekInYear(year, number);
  }
}
