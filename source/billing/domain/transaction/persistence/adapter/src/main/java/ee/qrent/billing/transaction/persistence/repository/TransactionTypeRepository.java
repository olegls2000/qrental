package ee.qrent.billing.transaction.persistence.repository;

import ee.qrent.billing.transaction.persistence.entity.jakarta.type.TransactionTypeJakartaEntity;
import java.util.List;

public interface TransactionTypeRepository {
  TransactionTypeJakartaEntity save(final TransactionTypeJakartaEntity entity);

  void deleteById(final Long id);

  List<TransactionTypeJakartaEntity> findAll();

  TransactionTypeJakartaEntity getReferenceById(final Long id);

  TransactionTypeJakartaEntity findByCode(final String code);

  List<TransactionTypeJakartaEntity> findAllByCodeIn(final List<String> codes);

  List<TransactionTypeJakartaEntity> findAllByKindCodesIn(final List<String> kindCodes);
}
