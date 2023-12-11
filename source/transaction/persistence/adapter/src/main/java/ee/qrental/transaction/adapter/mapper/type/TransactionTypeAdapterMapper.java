package ee.qrental.transaction.adapter.mapper.type;

import ee.qrental.transaction.adapter.mapper.kind.TransactionKindAdapterMapper;
import ee.qrental.transaction.domain.type.TransactionType;
import ee.qrental.transaction.entity.jakarta.type.TransactionTypeJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeAdapterMapper {

  private TransactionKindAdapterMapper transactionKindAdapterMapper;

  public TransactionType mapToDomain(final TransactionTypeJakartaEntity entity) {

    return TransactionType.builder()
        .id(entity.getId())
        .name(entity.getName())
        .description(entity.getDescription())
        .invoiceName(entity.getInvoiceName())
        .invoiceIncluded(entity.getInvoiceIncluded())
        .kind(transactionKindAdapterMapper.mapToDomain(entity.getKind()))
        .comment(entity.getComment())
        .build();
  }

  public TransactionTypeJakartaEntity mapToEntity(final TransactionType domain) {

    return TransactionTypeJakartaEntity.builder()
        .id(domain.getId())
        .name(domain.getName())
        .description(domain.getDescription())
        .invoiceName(domain.getInvoiceName())
        .invoiceIncluded(domain.getInvoiceIncluded())
        .comment(domain.getComment())
        .kind(transactionKindAdapterMapper.mapToEntity(domain.getKind()))
        .build();
  }
}
