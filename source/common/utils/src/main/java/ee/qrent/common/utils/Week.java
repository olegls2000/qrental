package ee.qrent.common.utils;

import java.time.LocalDate;

public record Week(int year, int weekNumber, LocalDate start, LocalDate end) {
  @Override
  public String toString() {
    return "Week{" + "start=" + start + ", end=" + end + ", weekNumber=" + weekNumber + '}';
  }
}
