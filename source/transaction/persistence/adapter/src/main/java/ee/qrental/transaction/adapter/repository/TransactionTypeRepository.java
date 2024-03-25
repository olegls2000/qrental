package ee.qrental.transaction.adapter.repository;

import ee.qrental.transaction.entity.jakarta.type.TransactionTypeJakartaEntity;
import java.util.List;

public interface TransactionTypeRepository {
  TransactionTypeJakartaEntity save(final TransactionTypeJakartaEntity entity);

  void deleteById(final Long id);

  List<TransactionTypeJakartaEntity> findAll();

  TransactionTypeJakartaEntity getReferenceById(final Long id);

  TransactionTypeJakartaEntity findByName(final String name);

  List<TransactionTypeJakartaEntity> findAllByNameIn(final List<String> names);

  List<TransactionTypeJakartaEntity> findAllByKindCodesIn(final List<String> kindCodes);
}
