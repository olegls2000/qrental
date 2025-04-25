package ee.qrent.billing.transaction.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrent.billing.transaction.domain.Transaction;
import ee.qrent.billing.transaction.domain.type.TransactionType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionUpdateRequestMapper
    implements UpdateRequestMapper<TransactionUpdateRequest, Transaction> {

  @Override
  public Transaction toDomain(final TransactionUpdateRequest request) {
    return Transaction.builder()
        .id(request.getId())
        .driverId(request.getDriverId())
        .type(TransactionType.builder().id(getTransactionTypeId(request)).build())
        .amount(request.getAmount())
        .date(request.getDate())
        .comment(request.getComment())
        .build();
  }

  private Long getTransactionTypeId(final TransactionUpdateRequest updateRequest) {
    final var positiveTransactionTypeId = updateRequest.getPositiveTransactionTypeId();
    if (positiveTransactionTypeId != null) {
      return positiveTransactionTypeId;
    }

    return updateRequest.getNegativeTransactionTypeId();
  }

  @Override
  public TransactionUpdateRequest toRequest(final Transaction domain) {
    final var updateRequest =
        TransactionUpdateRequest.builder()
            .id(domain.getId())
            .driverId(domain.getDriverId())
            .amount(domain.getAmount())
            .comment(domain.getComment())
            .date(domain.getDate())
            .build();
    setTransactionTypeToUpdateRequest(domain, updateRequest);

    return updateRequest;
  }

  private void setTransactionTypeToUpdateRequest(
      final Transaction domain, final TransactionUpdateRequest updateRequest) {
    final var isNegative = domain.getType().isNegative();
    final var transactionTypeId = domain.getType().getId();
    if (isNegative) {
      updateRequest.setNegativeTransactionTypeId(transactionTypeId);
    } else {
      updateRequest.setPositiveTransactionTypeId(transactionTypeId);
    }
  }
}
