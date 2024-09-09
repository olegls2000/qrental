package ee.qrental.insurance.core.service.balance;

import static java.math.BigDecimal.ZERO;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.in.query.GetInsuranceCaseBalanceQuery;
import ee.qrental.insurance.api.in.response.InsuranceBalanceTotalResponse;
import ee.qrental.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.domain.InsuranceCase;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import java.math.BigDecimal;
import java.util.ArrayList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalanceQueryService implements GetInsuranceCaseBalanceQuery {
  private final GetQWeekQuery qWeekQuery;
  private final InsuranceCaseBalanceLoadPort balanceLoadPort;
  private final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator;
  private final InsuranceCaseLoadPort insuranceCaseLoadPort;

  @Override
  public InsuranceBalanceTotalResponse getInsuranceBalanceTotalByDriverForCurrentWeek(
      final Long driverId) {
    final var currentQWeek = qWeekQuery.getCurrentWeek();

    return getInsuranceBalanceTotalByDriverIdAndQWeekId(driverId, currentQWeek.getId());
  }

  @Override
  public InsuranceBalanceTotalResponse getInsuranceBalanceTotalByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {

    final var balanceTotal =
        InsuranceBalanceTotalResponse.builder()
            .selfResponsibilityRemainingTotal(ZERO)
            .damageRemainingTotal(ZERO)
            .build();

    final var activeInsuranceCases =
        insuranceCaseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId);
    for (final var insuranceCase : activeInsuranceCases) {
      final var latestBalance = balanceLoadPort.loadLatestByInsuranceCaseId(insuranceCase.getId());
      if (latestBalance != null) {
        final var newSelfResponsibility =
            getNewSelfResponsibilityTotal(balanceTotal, latestBalance);
        final var newDamage = getNewDamageTotal(balanceTotal, latestBalance);
        balanceTotal.setDamageRemainingTotal(newDamage);
        balanceTotal.setSelfResponsibilityRemainingTotal(newSelfResponsibility);
      } else {
        final var firstBalance = getFirstInsuranceCaseBalance(qWeekId, insuranceCase);
        final var newSelfResponsibility = getNewSelfResponsibilityTotal(balanceTotal, firstBalance);
        final var newDamage = getNewDamageTotal(balanceTotal, firstBalance);
        balanceTotal.setDamageRemainingTotal(newDamage);
        balanceTotal.setSelfResponsibilityRemainingTotal(newSelfResponsibility);
      }
    }
    return balanceTotal;
  }

  private static BigDecimal getNewSelfResponsibilityTotal(
      final InsuranceBalanceTotalResponse balanceTotal, final InsuranceCaseBalance balance) {
    return balanceTotal
        .getSelfResponsibilityRemainingTotal()
        .add(balance.getSelfResponsibilityRemaining());
  }

  private static BigDecimal getNewDamageTotal(
      final InsuranceBalanceTotalResponse balanceTotal, final InsuranceCaseBalance balance) {
    return balanceTotal.getDamageRemainingTotal().add(balance.getDamageRemaining());
  }

  private InsuranceCaseBalance getFirstInsuranceCaseBalance(
      final Long qWeekId, final InsuranceCase insuranceCase) {
    final var firstBalance =
        InsuranceCaseBalance.builder()
            .insuranceCase(insuranceCase)
            .transactionIds(new ArrayList<>())
            .qWeekId(qWeekId)
            .build();
    insuranceCaseBalanceCalculator.setFirstRemainingAndSelfResponsibility(
        insuranceCase, firstBalance);

    return firstBalance;
  }
}
