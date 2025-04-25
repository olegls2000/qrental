package ee.qrent.billing.car.domain;

import lombok.Getter;

public enum CarStatus {
  IN_USE("In use"),
  BRISKO_PARKING("Brisko parking"),
  BRISKO_SERVICE("Brisko remont"),
  SKODA_PARKING("Skoda parking"),
  SKODA_SERVICE("Skoda remont"),
  SIKUPILLI_PARKING("Sikupilli parking"),
  FORSS_SERVICE("Forss remont");

  @Getter private String label;

  CarStatus(final String label) {
    this.label = label;
  }
}
