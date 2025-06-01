package ee.qrent.billing.insurance.core.service.strategy;

import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;

import java.time.LocalDate;
import java.time.Month;

public interface InsuranceCalculationStrategy {
    LocalDate NEW_CONTRACTS_START_DATE = LocalDate.of(2025, Month.APRIL, 28);

  boolean canApply(final DriverResponse driver, final QWeekResponse qWeek);

  void apply(
      final DriverResponse driver,
      final QWeekResponse qWeek,
      final InsuranceCalculation calculation);
}
