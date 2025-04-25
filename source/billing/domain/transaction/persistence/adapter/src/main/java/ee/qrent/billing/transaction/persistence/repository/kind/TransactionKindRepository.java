package ee.qrent.billing.transaction.persistence.repository.kind;

import ee.qrent.billing.transaction.persistence.entity.jakarta.kind.TransactionKindJakartaEntity;
import java.util.List;

public interface TransactionKindRepository {
  TransactionKindJakartaEntity save(final TransactionKindJakartaEntity entity);

  void deleteById(final Long id);

  List<TransactionKindJakartaEntity> findAll();

  TransactionKindJakartaEntity getReferenceById(final Long id);

  TransactionKindJakartaEntity findByName(final String name);

  TransactionKindJakartaEntity findByCode(final String code);

  List<TransactionKindJakartaEntity> findAllByCodeIn(final List<String> codes);
}
