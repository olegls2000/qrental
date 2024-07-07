package ee.qrental.transaction.core.mapper.type;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.transaction.api.in.response.type.TransactionTypeResponse;
import ee.qrental.transaction.domain.type.TransactionType;

public class TransactionTypeResponseMapper
    implements ResponseMapper<TransactionTypeResponse, TransactionType> {

  @Override
  public TransactionTypeResponse toResponse(final TransactionType domain) {
    final var kind = domain.getKind();
    final var kindCode = kind == null ? "n/a" : kind.getCode();

    return TransactionTypeResponse.builder()
        .id(domain.getId())
        .name(domain.getName())
        .negative(domain.isNegative())
        .feeAble(domain.isFeeAble())
        .kind(kindCode)
        .description(domain.getDescription())
        .invoiceName(domain.getInvoiceName())
        .invoiceIncluded(domain.getInvoiceIncluded())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(TransactionType domain) {
    return format("Transaction type : %s ", domain.getName());
  }
}
