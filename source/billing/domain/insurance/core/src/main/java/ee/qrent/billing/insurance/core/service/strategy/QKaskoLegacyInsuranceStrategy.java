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
      final InsuranceCaseUpdatePort caseUpdatePort,
      final InsuranceCaseLoadPort caseLoadPort,
      final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator) {
    super(contractQuery, caseUpdatePort);
    this.caseLoadPort = caseLoadPort;
    this.caseUpdatePort = caseUpdatePort;
    this.insuranceCaseBalanceCalculator = insuranceCaseBalanceCalculator;
  }

  @Override
  public boolean canApply(
      final DriverResponse driver, final QWeekResponse qWeek, final InsuranceCase insuranceCase) {
    final var contract =
        getContractQuery().getActiveByDriverIdAndQWeekId(driver.getId(), qWeek.getId());
    if (contract == null) {

      return false;
    }
    final var contractStartDate = contract.getDateStart();

    return contractStartDate.isBefore(NEW_CONTRACTS_START_DATE);
  }

  @Override
  public void apply(
      final DriverResponse driver,
      final QWeekResponse qWeek,
      final InsuranceCalculation calculation,
      final InsuranceCase insuranceCase) {
    final var requestedWeekBalance =
        insuranceCaseBalanceCalculator.calculateBalance(insuranceCase, qWeek);
    calculation.getInsuranceCaseBalances().add(requestedWeekBalance);
    checkAndDeactivateIfNecessary(requestedWeekBalance, insuranceCase);
  }
}
