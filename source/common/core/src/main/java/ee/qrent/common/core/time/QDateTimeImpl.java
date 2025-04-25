package ee.qrent.common.core.time;

import ee.qrent.common.in.time.QDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class QDateTimeImpl implements QDateTime {

  @Override
  public LocalDateTime getNow() {
    return LocalDateTime.now();
  }

  @Override
  public LocalDate getToday() {
    return LocalDate.now();
  }
}
