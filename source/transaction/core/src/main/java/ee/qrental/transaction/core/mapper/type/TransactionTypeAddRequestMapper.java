package ee.qrental.transaction.core.mapper.type;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.transaction.api.in.request.type.TransactionTypeAddRequest;
import ee.qrental.transaction.domain.type.TransactionType;

public class TransactionTypeAddRequestMapper
    implements AddRequestMapper<TransactionTypeAddRequest, TransactionType> {

  @Override
  public TransactionType toDomain(TransactionTypeAddRequest request) {
    return TransactionType.builder()
        .id(null)
        .name(request.getName())
        .negative(request.getNegative())
        .description(request.getDescription())
        .comment(request.getComment())
        .build();
  }
}
