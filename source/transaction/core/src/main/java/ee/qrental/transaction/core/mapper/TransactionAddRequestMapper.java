package ee.qrental.transaction.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.domain.Transaction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionAddRequestMapper
    implements AddRequestMapper<TransactionAddRequest, Transaction> {

  private final TransactionTypeLoadPort transactionTypeLoadPort;

  @Override
  public Transaction toDomain(TransactionAddRequest request) {
    final var transactionType = transactionTypeLoadPort.loadById(request.getTransactionTypeId());

    return Transaction.builder()
        .id(null)
        .type(transactionType)
        .amount(request.getAmount())
        .driverId(request.getDriverId())
        .date(request.getDate())
        .comment(request.getComment())
        .build();
  }
}
