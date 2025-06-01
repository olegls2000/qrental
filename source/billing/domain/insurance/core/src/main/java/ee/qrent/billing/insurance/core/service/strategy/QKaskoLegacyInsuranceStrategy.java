package ee.qrent.billing.insurance.core.service.strategy;

import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.billing.insurance.core.service.InsuranceCaseBalanceCalculator;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

public class QKaskoLegacyInsuranceStrategy extends AbstractInsuranceCalculationStrategy {

  private final InsuranceCaseLoadPort caseLoadPort;
  private final InsuranceCaseUpdatePort caseUpdatePort;
  private final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator;

  public QKaskoLegacyInsuranceStrategy(
      final GetContractQuery contractQuery,
      final InsuranceCaseLoadPort caseLoadPort,
      final InsuranceCaseUpdatePort caseUpdatePort,
      final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator) {
    super(contractQuery);
    this.caseLoadPort = caseLoadPort;
    this.caseUpdatePort = caseUpdatePort;
    this.insuranceCaseBalanceCalculator = insuranceCaseBalanceCalculator;
  }

  @Override
  public boolean canApply(final DriverResponse driver, final QWeekResponse qWeek) {
    final var contract =
        getContractQuery().getActiveByDriverIdAndQWeekId(driver.getId(), qWeek.getId());

    return contract.getDateStart().isBefore(NEW_CONTRACTS_START_DATE);
  }

  @Override
  public void apply(
      final DriverResponse driver,
      final QWeekResponse qWeek,
      final InsuranceCalculation calculation) {
    final var driverId = driver.getId();
    final var qWeekId = qWeek.getId();
    final var driverInfo =
        format(
            "Driver: %s %s, tax number: %d",
            driver.getFirstName(), driver.getLastName(), driver.getTaxNumber());
    final var weekInfo = format("QWeek: %d - %d", qWeek.getYear(), qWeek.getNumber());
    final var strategyInfo = this.getClass().getSimpleName();
    System.out.println(
        format("Strategy %s, will be applied for %s and %s", strategyInfo, driverInfo, weekInfo));

    final var activeCases = caseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId);
    if (activeCases.isEmpty()) {
      System.out.println(format("No Active insurance cases for %s and %s", driverInfo, weekInfo));

      return;
    }
    final var activeCaseForProcessing = activeCases.stream().findFirst().get();
    final var requestedWeekBalance =
        insuranceCaseBalanceCalculator.calculateBalance(activeCaseForProcessing, qWeek);
    calculation.getInsuranceCaseBalances().add(requestedWeekBalance);
    checkAndDeactivateIfNecessary(requestedWeekBalance, activeCaseForProcessing);
  }

  private void checkAndDeactivateIfNecessary(
      final InsuranceCaseBalance balance, final InsuranceCase insuranceCase) {
    if (balance.getDamageRemaining().compareTo(ZERO) == 0
        && balance.getSelfResponsibilityRemaining().compareTo(ZERO) == 0) {
      insuranceCase.setActive(false);
      caseUpdatePort.update(insuranceCase);
    }
  }
}
