package ee.qrent.billing.transaction.persistence.adapter.balance;

import ee.qrent.billing.transaction.persistence.mapper.balance.BalanceAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceCalculationRepository;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceCalculationResultRepository;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceTransactionRepository;
import ee.qrent.billing.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrent.billing.transaction.domain.balance.BalanceCalculation;
import ee.qrent.billing.transaction.domain.balance.BalanceCalculationResult;

import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceCalculationJakartaEntity;
import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;
import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceJakartaEntity;
import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceTransactionJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationPersistenceAdapter implements BalanceCalculationAddPort {

  private final BalanceCalculationRepository balanceCalculationRepository;
  private final BalanceCalculationResultRepository balanceCalculationResultRepository;
  private final BalanceTransactionRepository balanceTransactionRepository;
  private final BalanceAdapterMapper balanceMapper;

  @Override
  public BalanceCalculation add(final BalanceCalculation domain) {
    final var balanceCalculationEntity =
        BalanceCalculationJakartaEntity.builder()
            .actionDate(domain.getActionDate())
            .startDate(domain.getStartDate())
            .endDate(domain.getEndDate())
            .comment(domain.getComment())
            .build();
    final var balanceCalculationEntitySaved =
        balanceCalculationRepository.save(balanceCalculationEntity);
    saveBalanceCalculationResults(domain, balanceCalculationEntitySaved);

    return null;
  }

  private void saveBalanceCalculationResults(
      final BalanceCalculation domain,
      final BalanceCalculationJakartaEntity balanceCalculationEntitySaved) {
    final var balanceCalculationResults = domain.getResults();
    for (BalanceCalculationResult result : balanceCalculationResults) {
      final var balance = result.getBalance();
      final var balanceEntity = balanceMapper.mapToEntity(balance);
    final var balanceCalculationResultEntity =
          BalanceCalculationResultJakartaEntity.builder()
              .id(null)
              .calculation(balanceCalculationEntitySaved)
              .balance(balanceEntity)
              .build();
      balanceCalculationResultRepository.save(balanceCalculationResultEntity);

      saveBalanceTransactions(result, balanceEntity);
    }
  }

  private void saveBalanceTransactions(
      BalanceCalculationResult result, BalanceJakartaEntity balanceEntitySaved) {
    final var transactionIds = result.getTransactionIds();
    for (Long transactionId : transactionIds) {
      final var balanceTransactionEntity =
          BalanceTransactionJakartaEntity.builder()
              .balance(balanceEntitySaved)
              .transactionId(transactionId)
              .build();
      balanceTransactionRepository.save(balanceTransactionEntity);
    }
  }
}
