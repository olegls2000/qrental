package ee.qrent.billing.report.api.in.request;

import lombok.Getter;

public enum WeeklyReportTypeIn {
  INFO_REPORT("Info report"),
  TUESDAY_REPORT("Tuesday report"),
  WEDNESDAY_REPORT("Wednesday report"),
  FRIDAY_REPORT("Friday report");

  @Getter private String label;

  WeeklyReportTypeIn(final String label) {
    this.label = label;
  }
}
