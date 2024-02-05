package ee.qrental.transaction.adapter.mapper;

import ee.qrental.transaction.adapter.mapper.type.TransactionTypeAdapterMapper;
import ee.qrental.transaction.adapter.repository.balance.BalanceTransactionRepository;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.entity.jakarta.TransactionJakartaEntity;
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
