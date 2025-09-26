package ee.qrent.billing.transaction.core.mapper.type;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.transaction.api.in.response.type.TransactionTypeResponse;
import ee.qrent.billing.transaction.domain.type.TransactionType;

public class TransactionTypeResponseMapper
    implements ResponseMapper<TransactionTypeResponse, TransactionType> {

  @Override
  public TransactionTypeResponse toResponse(final TransactionType domain) {
    if (domain == null) {

        return null;
    }

    final var kind = domain.getKind();
    final var kindCode = kind == null ? "n/a" : kind.getCode();

    return TransactionTypeResponse.builder()
        .id(domain.getId())
        .negative(domain.isNegative())
        .feeAble(domain.isFeeAble())
        .kind(kindCode)
        .code(domain.getCode())
        .nameEng(domain.getNameEng())
        .nameRus(domain.getNameRus())
        .nameEst(domain.getNameEst())
        .invoiceIncluded(domain.getInvoiceIncluded())
        .uiVisible(domain.getUiVisible())
        .uiName(domain.getUiName())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(TransactionType domain) {
    return format("Transaction type : %s ", domain.getCode());
  }
}
