package ee.qrental.car.domain;

import lombok.Getter;

public enum CarStatus {
  BRISKO_PARKING("Brisko parking"),
  BRISKO_SERVICE("Brisko remont"),
  SKOD_PARKING("Skoda parking"),
  SKODA_SERVICE("Skoda remont"),
  SIKUPILI_PARKING("Sikupili parking"),
  FORSS_SERVICE("Forss remont");

  @Getter private String label;

  CarStatus(final String label) {
    this.label = label;
  }
}
