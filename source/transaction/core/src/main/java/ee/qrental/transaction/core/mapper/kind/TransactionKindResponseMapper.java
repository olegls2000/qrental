package ee.qrental.transaction.core.mapper.kind;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.transaction.api.in.response.kind.TransactionKindResponse;
import ee.qrental.transaction.domain.kind.TransactionKind;

public class TransactionKindResponseMapper
    implements ResponseMapper<TransactionKindResponse, TransactionKind> {

  @Override
  public TransactionKindResponse toResponse(final TransactionKind domain) {
    return TransactionKindResponse.builder()
        .id(domain.getId())
        .name(domain.getName())
        .code(domain.getCode())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(TransactionKind domain) {
    return format("Transaction type : %s ", domain.getName());
  }
}
