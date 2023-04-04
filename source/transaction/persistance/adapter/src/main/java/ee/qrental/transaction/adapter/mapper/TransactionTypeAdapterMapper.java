package ee.qrental.transaction.adapter.mapper;

import ee.qrental.transaction.domain.TransactionType;
import ee.qrental.transaction.entity.jakarta.TransactionTypeJakartaEntity;

public class TransactionTypeAdapterMapper {
  public TransactionType mapToDomain(final TransactionTypeJakartaEntity entity) {
    return TransactionType.builder()
        .id(entity.getId())
        .name(entity.getName())
        .negative(entity.getNegative())
        .description(entity.getDescription())
        .comment(entity.getComment())
        .build();
  }

  public TransactionTypeJakartaEntity mapToEntity(final TransactionType domain) {
    return TransactionTypeJakartaEntity.builder()
        .id(domain.getId())
        .name(domain.getName())
        .description(domain.getDescription())
        .negative(domain.getNegative())
        .comment(domain.getComment())
        .build();
  }
}
