package ee.qrent.billing.insurance.core.service.balance;

import static ee.qrent.common.utils.QNumberUtils.qRound;
import static java.math.BigDecimal.ZERO;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCaseBalanceQuery;
import ee.qrent.billing.insurance.api.in.response.InsuranceBalanceTotalResponse;
import ee.qrent.billing.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.core.service.InsuranceCaseBalanceCalculator;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.filter.PeriodAndKindAndDriverTransactionFilter;
import ee.qrent.billing.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.in.response.kind.TransactionKindResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalanceQueryService implements GetInsuranceCaseBalanceQuery {
  private final GetQWeekQuery qWeekQuery;
  private final InsuranceCaseBalanceLoadPort balanceLoadPort;
  private final InsuranceCaseBalanceCalculator insuranceCaseBalanceCalculator;
  private final InsuranceCaseLoadPort insuranceCaseLoadPort;
  private final GetInsuranceCalculationQuery insuranceCalculationQuery;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionKindQuery transactionKindQuery;

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
            .selfResponsibilityRemainingTotal(qRound(ZERO))
            .damageRemainingTotal(qRound(ZERO))
            .damageInitialTotal(qRound(ZERO))
            .build();

    final var activeInsuranceCases =
        insuranceCaseLoadPort.loadActiveByDriverIdAndQWeekId(driverId, qWeekId);

    for (final var insuranceCase : activeInsuranceCases) {
      sumInitialDamage(insuranceCase, balanceTotal);
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

    final var paidSelfResponsibilityTotal = getPaidSelfResponsibilityTotal(driverId, qWeekId);
    final var adjustedSelfResponsibility =
        balanceTotal.getSelfResponsibilityRemainingTotal().subtract(paidSelfResponsibilityTotal);
    balanceTotal.setSelfResponsibilityRemainingTotal(adjustedSelfResponsibility);

    return roundAmounts(balanceTotal);
  }

  private static InsuranceBalanceTotalResponse roundAmounts(
      final InsuranceBalanceTotalResponse balance) {
    balance.setDamageInitialTotal(qRound(balance.getDamageInitialTotal()));
    balance.setDamageRemainingTotal(qRound(balance.getDamageRemainingTotal()));
    balance.setSelfResponsibilityRemainingTotal(
        qRound(balance.getSelfResponsibilityRemainingTotal()));

    return balance;
  }

  private void sumInitialDamage(
      final InsuranceCase insuranceCase, final InsuranceBalanceTotalResponse balanceTotal) {
    final var currentInitialTotal = balanceTotal.getDamageInitialTotal();
    balanceTotal.setDamageInitialTotal(currentInitialTotal.add(insuranceCase.getDamageAmount()));
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

  private BigDecimal getPaidSelfResponsibilityTotal(final Long driverId, final Long qWeekEndId) {
    final var transactionKindIds =
        transactionKindQuery.getAllSelfResponsibility().stream()
            .map(TransactionKindResponse::getId)
            .toList();
    final var startQWeekId = insuranceCalculationQuery.getStartQWeekId();
    final var startQWeek = qWeekQuery.getById(startQWeekId);
    final var endQWeek = qWeekQuery.getById(qWeekEndId);
    final var filter =
        PeriodAndKindAndDriverTransactionFilter.builder()
            .dateStart(startQWeek.getStart())
            .dateEnd(endQWeek.getEnd())
            .driverId(driverId)
            .transactionKindIds(transactionKindIds)
            .build();

    return transactionQuery.getAllByFilter(filter).stream()
        .map(TransactionResponse::getRealAmount)
        .map(BigDecimal::abs)
        .reduce(BigDecimal::add)
        .orElse(ZERO);
  }
}
