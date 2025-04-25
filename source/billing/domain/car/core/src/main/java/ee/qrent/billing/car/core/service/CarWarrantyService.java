package ee.qrent.billing.car.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.car.domain.Car;
import ee.qrent.billing.car.domain.RagStatus;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

@AllArgsConstructor
public class CarWarrantyService {

  private static final int WARRANTY_PERIOD_IN_YEARS = 2;
  private static final int GREEN_THRESHOLD_IN_DAYS = 610;
  private static final int AMBER_THRESHOLD_IN_DAYS = 550;

  private final QDateTime qDateTime;

  public LocalDate getWarrantyEndDate(final Car car) {

    return car.getReleaseDate().plusYears(WARRANTY_PERIOD_IN_YEARS);
  }

  public Long getWarrantyMonths(final Car car) {
    final var warrantyEndDate = getWarrantyEndDate(car);
    final var today = qDateTime.getToday();
    if (warrantyEndDate.isBefore(today) || today.equals(warrantyEndDate)) {

      return 0L;
    }

    return MONTHS.between(today, warrantyEndDate);
  }

  public RagStatus getWarrantyRagStatus(final Car car) {
    final var warrantyEndDate = getWarrantyEndDate(car);
    final var today = qDateTime.getToday();

    if (warrantyEndDate.isBefore(today) || today.equals(warrantyEndDate)) {

      return RagStatus.RED;
    }

    final var warrantyPeriodInDays = DAYS.between(today, warrantyEndDate);

    if (warrantyPeriodInDays > GREEN_THRESHOLD_IN_DAYS) {

      return RagStatus.GREEN;
    }

    if (warrantyPeriodInDays > AMBER_THRESHOLD_IN_DAYS) {

      return RagStatus.AMBER;
    }

    return RagStatus.RED;
  }
}
