package ee.qrent.billing.report.api.in.request;

import lombok.Getter;

public enum WeeklyReportType {
  MONDAY_REPORT("Monday report"),
  TUESDAY_REPORT("Tuesday report"),
  WEDNESDAY_REPORT("Wednesday report"),
  FRIDAY_REPORT("Friday report");

  @Getter private String label;

  WeeklyReportType(final String label) {
    this.label = label;
  }
  ;
}
