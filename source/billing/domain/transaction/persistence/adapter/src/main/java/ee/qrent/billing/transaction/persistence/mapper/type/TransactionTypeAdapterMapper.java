package ee.qrent.billing.transaction.persistence.mapper.type;

import ee.qrent.billing.transaction.persistence.mapper.kind.TransactionKindAdapterMapper;
import ee.qrent.billing.transaction.domain.type.TransactionType;
import ee.qrent.billing.transaction.persistence.entity.jakarta.type.TransactionTypeJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeAdapterMapper {

  private TransactionKindAdapterMapper transactionKindAdapterMapper;

  public TransactionType mapToDomain(final TransactionTypeJakartaEntity entity) {

    return TransactionType.builder()
        .id(entity.getId())
        .code(entity.getCode())
        .nameEng(entity.getNameEng())
        .nameRus(entity.getNameRus())
        .nameEst(entity.getNameEst())
        .invoiceIncluded(entity.getInvoiceIncluded())
        .visibleForUi(entity.getVisibleForUi())
        .uiName(entity.getUiName())
        .kind(transactionKindAdapterMapper.mapToDomain(entity.getKind()))
        .comment(entity.getComment())
        .build();
  }

  public TransactionTypeJakartaEntity mapToEntity(final TransactionType domain) {

    return TransactionTypeJakartaEntity.builder()
        .id(domain.getId())
        .code(domain.getCode())
        .nameEng(domain.getNameEng())
        .nameRus(domain.getNameRus())
        .nameEst(domain.getNameEst())
        .invoiceIncluded(domain.getInvoiceIncluded())
        .visibleForUi((domain.getVisibleForUi()))
        .uiName(domain.getUiName())
        .comment(domain.getComment())
        .kind(transactionKindAdapterMapper.mapToEntity(domain.getKind()))
        .build();
  }
}
