package ee.qrental.transaction.adapter.adapter.balance;

import ee.qrental.transaction.adapter.mapper.balance.BalanceAdapterMapper;
import ee.qrental.transaction.adapter.repository.balance.BalanceCalculationRepository;
import ee.qrental.transaction.adapter.repository.balance.BalanceCalculationResultRepository;
import ee.qrental.transaction.adapter.repository.balance.BalanceTransactionRepository;
import ee.qrental.transaction.api.out.balance.BalanceCalculationAddPort;
import ee.qrental.transaction.domain.balance.BalanceCalculation;
import ee.qrental.transaction.domain.balance.BalanceCalculationResult;

import ee.qrental.transaction.entity.jakarta.balance.BalanceCalculationJakartaEntity;
import ee.qrental.transaction.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;
import ee.qrental.transaction.entity.jakarta.balance.BalanceJakartaEntity;
import ee.qrental.transaction.entity.jakarta.balance.BalanceTransactionJakartaEntity;
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
