package ee.qrental.transaction.core.mapper;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.type.TransactionType;

public class TransactionAddRequestMapper
    implements AddRequestMapper<TransactionAddRequest, Transaction> {

  @Override
  public Transaction toDomain(TransactionAddRequest request) {
    return Transaction.builder()
        .id(null)
        .type(TransactionType.builder().id(request.getTransactionTypeId()).build())
        .amount(request.getAmount())
        .driverId(request.getDriverId())
        .date(request.getDate())
        .withVat(request.getWithVat())
        .comment(request.getComment())
        .build();
  }
}
