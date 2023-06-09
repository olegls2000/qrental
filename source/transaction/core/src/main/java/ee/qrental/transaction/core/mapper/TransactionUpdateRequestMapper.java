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
        .type(TransactionType.builder().id(getTransactionTypeId(request)).build())
        .amount(request.getAmount())
        .date(request.getDate())
        .withVat(request.getWithVat())
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
  public TransactionUpdateRequest toRequest(Transaction domain) {
    final var updateRequest =
        TransactionUpdateRequest.builder()
            .id(domain.getId())
            .driverId(domain.getDriverId())
            .amount(domain.getAmount())
            .withVat(domain.getWithVat())
            .comment(domain.getComment())
            .date(domain.getDate())
            .build();
    setTransactionTypeToUpdateRequest(domain, updateRequest);

    return updateRequest;
  }

  private void setTransactionTypeToUpdateRequest(
      final Transaction domain, final TransactionUpdateRequest updateRequest) {
    final var isNegative = domain.getType().getNegative();
    final var transactionTypeId = domain.getType().getId();
    if (isNegative) {
      updateRequest.setNegativeTransactionTypeId(transactionTypeId);
    } else {
      updateRequest.setPositiveTransactionTypeId(transactionTypeId);
    }
  }
}
