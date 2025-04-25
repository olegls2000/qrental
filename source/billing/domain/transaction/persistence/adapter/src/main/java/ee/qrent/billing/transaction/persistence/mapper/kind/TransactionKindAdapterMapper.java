package ee.qrent.billing.transaction.persistence.mapper.kind;

import ee.qrent.billing.transaction.domain.kind.TransactionKind;
import ee.qrent.billing.transaction.persistence.entity.jakarta.kind.TransactionKindJakartaEntity;

public class TransactionKindAdapterMapper {
  public TransactionKind mapToDomain(final TransactionKindJakartaEntity entity) {
    if (entity == null) {

      return null;
    }

    return TransactionKind.builder()
        .id(entity.getId())
        .name(entity.getName())
        .code(entity.getCode())
        .comment(entity.getComment())
        .build();
  }

  public TransactionKindJakartaEntity mapToEntity(final TransactionKind domain) {
    if (domain == null) {
      return null;
    }
    return TransactionKindJakartaEntity.builder()
        .id(domain.getId())
        .code(domain.getCode())
        .name(domain.getName())
        .comment(domain.getComment())
        .build();
  }
}
