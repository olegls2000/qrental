package ee.qrent.common.core.enums;

import lombok.Getter;

public enum AbsenceReason {
  VACATION("Vacation"),
  SICK_LEAVE("Sick Leave");

  @Getter private String displayValue;

  AbsenceReason(final String displayValue) {
    this.displayValue = displayValue;
  }
}
