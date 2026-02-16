package ee.qrent.billing.car.persistence.repository;

import java.time.LocalDate;

public interface BrandingVerificationCalculationSummaryProjection {
  Long getCalculationId();

  LocalDate getActionDate();

  String getType();

  Long getVerificationsCount();
}
