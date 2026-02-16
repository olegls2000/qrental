package ee.qrent.billing.car.persistence.repository;

import java.time.LocalDate;

public interface BrandingVerificationCalculationResultProjection {
  Long getCalculationId();

  LocalDate getActionDate();

  String getComment();

  String getType();

  Long getVerificationId();

  LocalDate getVerificationDate();

  Long getDriverId();

  String getDriverFirstName();

  String getDriverLastName();

  Long getCarId();

  String getCarRegNumber();

  Long getCarLinkId();

  LocalDate getCarLinkStartDate();

  LocalDate getBrandingExpirationDate();
}
