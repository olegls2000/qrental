package ee.qrent.billing.transaction.core.mapper.type;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrent.billing.transaction.domain.kind.TransactionKind;
import ee.qrent.billing.transaction.domain.type.TransactionType;

public class TransactionTypeUpdateRequestMapper
    implements UpdateRequestMapper<TransactionTypeUpdateRequest, TransactionType> {

  @Override
  public TransactionType toDomain(final TransactionTypeUpdateRequest request) {
    return TransactionType.builder()
        .id(request.getId())
        .name(request.getName())
        .description(request.getDescription())
        .invoiceName(request.getInvoiceName())
        .code(request.getCode())
        .nameEng(request.getNameEng())
        .nameRus(request.getNameRus())
        .nameEst(request.getNameEst())
        .invoiceIncluded(request.getInvoiceIncluded())
        .visibleForUi(request.getVisibleForUi())
        .kind(TransactionKind.builder().id(request.getTransactionKindId()).build())
        .comment(request.getComment())
        .build();
  }

  @Override
  public TransactionTypeUpdateRequest toRequest(final TransactionType domain) {
    final var kind = domain.getKind();
    final var kindId = kind == null ? null : kind.getId();

    return TransactionTypeUpdateRequest.builder()
        .id(domain.getId())
        .name(domain.getName())
        .description(domain.getDescription())
        .invoiceName(domain.getInvoiceName())
        .code(domain.getCode())
        .nameEng(domain.getNameEng())
        .nameRus(domain.getNameRus())
        .nameEst(domain.getNameEst())
        .invoiceIncluded(domain.getInvoiceIncluded())
        .visibleForUi(domain.getVisibleForUi())
        .transactionKindId(kindId)
        .comment(domain.getComment())
        .build();
  }
}
