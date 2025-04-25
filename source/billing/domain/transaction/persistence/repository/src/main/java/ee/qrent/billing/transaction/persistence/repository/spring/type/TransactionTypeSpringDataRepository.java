package ee.qrent.billing.transaction.persistence.repository.spring.type;

import ee.qrent.billing.transaction.persistence.entity.jakarta.type.TransactionTypeJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionTypeSpringDataRepository
    extends JpaRepository<TransactionTypeJakartaEntity, Long> {

  TransactionTypeJakartaEntity findByName(final String name);

  @Query(
      value =
          "SELECT * FROM transaction_type tx "
              + "WHERE tx.transaction_kind_id  in ("
              + "select txk.id from transaction_kind txk "
              + "where txk.code in :kindCodes) ",
      nativeQuery = true)
  List<TransactionTypeJakartaEntity> findAllByKindCodesIn(
      @Param("kindCodes") final List<String> kindCodes);

  @Query(value = "SELECT * FROM transaction_type txt WHERE txt.name in :names", nativeQuery = true)
  List<TransactionTypeJakartaEntity> findAllByNameIn(@Param("names") final List<String> names);
}
