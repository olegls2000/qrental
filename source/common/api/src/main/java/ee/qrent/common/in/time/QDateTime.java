package ee.qrent.common.in.time;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface QDateTime {
  LocalDateTime getNow();

  LocalDate getToday();
}
