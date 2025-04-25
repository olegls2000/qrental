package ee.qrent.billing.transaction.core.mapper.kind;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindAddRequest;
import ee.qrent.billing.transaction.domain.kind.TransactionKind;

public class TransactionKindAddRequestMapper
    implements AddRequestMapper<TransactionKindAddRequest, TransactionKind> {

  @Override
  public TransactionKind toDomain(final TransactionKindAddRequest request) {
    return TransactionKind.builder()
        .id(null)
        .name(request.getName())
        .code(request.getCode())
        .comment(request.getComment())
        .build();
  }
}
