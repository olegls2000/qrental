package ee.qrental.contract.domain;

import lombok.Getter;

public enum ContractDuration {
  FOUR_WEEKS("4 weeks", 4),
  TWELVE_WEEKS("12 weeks", 12);

  @Getter private String label;
  @Getter private Integer weeksCount;

  ContractDuration(final String label, final Integer weeksCount) {
    this.label = label;
    this.weeksCount = weeksCount;
  }
}
