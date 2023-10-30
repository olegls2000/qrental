package ee.qrental.transaction.core.mapper.type;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.transaction.api.in.response.type.TransactionTypeResponse;
import ee.qrental.transaction.domain.type.TransactionType;

public class TransactionTypeResponseMapper
    implements ResponseMapper<TransactionTypeResponse, TransactionType> {

  @Override
  public TransactionTypeResponse toResponse(final TransactionType domain) {
    return TransactionTypeResponse.builder()
        .id(domain.getId())
        .name(domain.getName())
        .negative(domain.getNegative())
        .feeAble(domain.getFeeAble())
        .description(domain.getDescription())
        .descriptionRus(domain.getDescriptionRus())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(TransactionType domain) {
    return format("Transaction type : %s ", domain.getName());
  }
}
