package ee.qrent.billing.driver.domain;

import lombok.Getter;

public enum LegalEntityType {
  NOT_DEFINED("Not defined"),
  LHV_ACCOUNT("LHV Bank account"),
  SELF_EMPLOYED("Self employed"),
  COMPANY("Company");

  @Getter private String label;

  LegalEntityType(String label) {
    this.label = label;
  }
}
