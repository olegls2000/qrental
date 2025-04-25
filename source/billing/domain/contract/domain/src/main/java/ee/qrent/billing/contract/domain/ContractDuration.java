package ee.qrent.billing.contract.domain;

import lombok.Getter;

public enum ContractDuration {
  FOUR_WEEKS("4 weeks", 4),
  TWELVE_WEEKS("12 weeks", 12);

  @Getter private final String label;
  @Getter private final Integer weeksCount;

  ContractDuration(final String label, final Integer weeksCount) {
    this.label = label;
    this.weeksCount = weeksCount;
  }
}
