package ee.qrent.billing.transaction.persistence.mapper;

import ee.qrent.billing.transaction.persistence.mapper.type.TransactionTypeAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.balance.BalanceTransactionRepository;
import ee.qrent.billing.transaction.domain.Transaction;
import ee.qrent.billing.transaction.persistence.entity.jakarta.TransactionJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionAdapterMapper {

  private final TransactionTypeAdapterMapper transactionTypeAdapterMapper;
  private final BalanceTransactionRepository transactionBalanceRepository;

  public Transaction mapToDomain(final TransactionJakartaEntity entity) {
    final var calculated =
        transactionBalanceRepository.isTransactionCalculatedInBalance(entity.getId());

    return Transaction.builder()
        .id(entity.getId())
        .driverId(entity.getDriverId())
        .amount(entity.getAmount())
        .type(transactionTypeAdapterMapper.mapToDomain(entity.getType()))
        .date(entity.getDate())
        .calculated(calculated)
        .comment(entity.getComment())
        .build();
  }

  public TransactionJakartaEntity mapToEntity(final Transaction domain) {
    return TransactionJakartaEntity.builder()
        .id(domain.getId())
        .type(transactionTypeAdapterMapper.mapToEntity(domain.getType()))
        .driverId(domain.getDriverId())
        .amount(domain.getAmount())
        .date(domain.getDate())
        .comment(domain.getComment())
        .build();
  }
}
