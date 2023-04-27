package ee.qrental.balance.adapter.adapter;

import ee.qrental.balance.adapter.mapper.BalanceAdapterMapper;
import ee.qrental.balance.adapter.repository.BalanceCalculationRepository;
import ee.qrental.balance.adapter.repository.BalanceCalculationResultRepository;
import ee.qrental.balance.adapter.repository.BalanceRepository;
import ee.qrental.balance.adapter.repository.BalanceTransactionRepository;
import ee.qrental.balance.api.out.BalanceCalculationAddPort;
import ee.qrental.balance.domain.BalanceCalculation;
import ee.qrental.balance.domain.BalanceCalculationResult;
import ee.qrental.invoice.entity.jakarta.BalanceCalculationJakartaEntity;
import ee.qrental.invoice.entity.jakarta.BalanceCalculationResultJakartaEntity;
import ee.qrental.invoice.entity.jakarta.BalanceJakartaEntity;
import ee.qrental.invoice.entity.jakarta.BalanceTransactionJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationPersistenceAdapter implements BalanceCalculationAddPort {

  private final BalanceCalculationRepository balanceCalculationRepository;
  private final BalanceCalculationResultRepository balanceCalculationResultRepository;
  private final BalanceRepository balanceRepository;
  private final BalanceTransactionRepository balanceTransactionRepository;
  private final BalanceAdapterMapper balanceMapper;

  @Override
  public BalanceCalculation add(final BalanceCalculation domain) {
    final var balanceCalculationEntity =
        BalanceCalculationJakartaEntity.builder()
            .actionDate(domain.getActionDate())
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
      final var balanceEntitySaved = balanceRepository.save(balanceEntity);
      final var balanceCalculationResultEntity =
          BalanceCalculationResultJakartaEntity.builder()
              .id(null)
              .calculation(balanceCalculationEntitySaved)
              .balance(balanceEntitySaved)
              .build();
      balanceCalculationResultRepository.save(balanceCalculationResultEntity);

      saveBalanceTransactions(result, balanceEntitySaved);
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
