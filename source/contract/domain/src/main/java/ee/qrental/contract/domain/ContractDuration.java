package ee.qrental.contract.domain;

import lombok.Getter;

public enum ContractDuration {
  FOUR_MONTHS("4 months", 4),
  TWELVE_MONTHS("12 months", 12);

  @Getter private String label;
  @Getter private Integer monthsCount;

  ContractDuration(String label, Integer monthsCount) {
    this.label = label;
    this.monthsCount = monthsCount;
  }
}
