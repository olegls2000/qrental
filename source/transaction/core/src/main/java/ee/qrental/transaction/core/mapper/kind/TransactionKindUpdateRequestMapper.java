package ee.qrental.transaction.core.mapper.kind;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrental.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrental.transaction.domain.kind.TransactionKind;

public class TransactionKindUpdateRequestMapper
    implements UpdateRequestMapper<TransactionKindUpdateRequest, TransactionKind> {

  @Override
  public TransactionKind toDomain(final TransactionKindUpdateRequest request) {
    return TransactionKind.builder()
        .id(request.getId())
        .name(request.getName())
        .comment(request.getComment())
        .build();
  }

  @Override
  public TransactionKindUpdateRequest toRequest(final TransactionKind domain) {
    return TransactionKindUpdateRequest.builder()
        .id(domain.getId())
        .name(domain.getName())
        .comment(domain.getComment())
        .build();
  }
}
