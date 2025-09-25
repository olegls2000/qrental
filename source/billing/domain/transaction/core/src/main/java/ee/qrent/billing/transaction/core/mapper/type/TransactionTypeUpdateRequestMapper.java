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
        .code(request.getCode())
        .nameEng(request.getNameEng())
        .nameRus(request.getNameRus())
        .nameEst(request.getNameEst())
        .invoiceIncluded(request.getInvoiceIncluded())
        .uiVisible(request.getUiVisible())
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
        .code(domain.getCode())
        .nameEng(domain.getNameEng())
        .nameRus(domain.getNameRus())
        .nameEst(domain.getNameEst())
        .invoiceIncluded(domain.getInvoiceIncluded())
        .uiVisible(domain.getUiVisible())
        .transactionKindId(kindId)
        .comment(domain.getComment())
        .build();
  }
}
