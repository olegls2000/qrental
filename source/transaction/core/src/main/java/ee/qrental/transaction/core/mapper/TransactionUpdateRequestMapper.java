package ee.qrental.transaction.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrental.transaction.domain.Transaction;
import ee.qrental.transaction.domain.TransactionType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionUpdateRequestMapper
    implements UpdateRequestMapper<TransactionUpdateRequest, Transaction> {

  @Override
  public Transaction toDomain(TransactionUpdateRequest request) {
    return Transaction.builder()
        .id(request.getId())
        .driverId(request.getDriverId())
        .type(TransactionType.builder().id(request.getTransactionTypeId()).build())
        .amount(request.getAmount())
        .date(request.getDate())
        .comment(request.getComment())
        .build();
  }

  @Override
  public TransactionUpdateRequest toRequest(Transaction domain) {
    return TransactionUpdateRequest.builder()
        .id(domain.getId())
        .transactionTypeId(domain.getType().getId())
        .driverId(domain.getDriverId())
        .amount(domain.getRealAmount())
        .comment(domain.getComment())
        .date(domain.getDate())
        .build();
  }
}
