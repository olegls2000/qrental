package ee.qrental.transaction.core.mapper.type;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.transaction.api.in.request.type.TransactionTypeAddRequest;
import ee.qrental.transaction.domain.kind.TransactionKind;
import ee.qrental.transaction.domain.type.TransactionType;

public class TransactionTypeAddRequestMapper
    implements AddRequestMapper<TransactionTypeAddRequest, TransactionType> {

  @Override
  public TransactionType toDomain(final TransactionTypeAddRequest request) {
    return TransactionType.builder()
        .id(null)
        .kind(TransactionKind.builder().id(request.getTransactionKindId()).build())
        .name(request.getName())
        .negative(request.getNegative())
        .feeAble(request.getFeeAble())
        .description(request.getDescription())
        .descriptionRus(request.getDescriptionRus())
        .comment(request.getComment())
        .build();
  }
}
