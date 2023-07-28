package ee.qrental.transaction.core.mapper.type;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrental.transaction.domain.type.TransactionType;

public class TransactionTypeUpdateRequestMapper
    implements UpdateRequestMapper<TransactionTypeUpdateRequest, TransactionType> {

  @Override
  public TransactionType toDomain(final TransactionTypeUpdateRequest request) {
    return TransactionType.builder()
        .id(request.getId())
        .name(request.getName())
        .negative(request.getNegative())
        .description(request.getDescription())
        .comment(request.getComment())
        .build();
  }

  @Override
  public TransactionTypeUpdateRequest toRequest(final TransactionType domain) {
    return TransactionTypeUpdateRequest.builder()
        .id(domain.getId())
        .name(domain.getName())
        .negative(domain.getNegative())
        .description(domain.getDescription())
        .comment(domain.getComment())
        .build();
  }
}
