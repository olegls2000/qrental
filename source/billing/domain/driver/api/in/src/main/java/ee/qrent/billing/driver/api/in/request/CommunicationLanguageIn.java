package ee.qrent.billing.driver.api.in.request;

import lombok.Getter;

public enum CommunicationLanguageIn {
  RUS("Russian"),
  EST("Estonian"),
  ENG("English");
  @Getter private final String label;

  CommunicationLanguageIn(String label) {
    this.label = label;
  }
  ;
}
