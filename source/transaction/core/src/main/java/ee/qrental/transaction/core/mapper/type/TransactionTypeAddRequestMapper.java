package ee.qrental.transaction.core.mapper.type;

import ee.qrent.common.in.mapper.AddRequestMapper;
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
        .description(request.getDescription())
        .invoiceName(request.getInvoiceName())
        .invoiceIncluded(request.getInvoiceIncluded())
        .comment(request.getComment())
        .build();
  }
}
