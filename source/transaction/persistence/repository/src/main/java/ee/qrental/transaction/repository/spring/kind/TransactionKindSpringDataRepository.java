package ee.qrental.transaction.repository.spring.kind;

import ee.qrental.transaction.entity.jakarta.kind.TransactionKindJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionKindSpringDataRepository
    extends JpaRepository<TransactionKindJakartaEntity, Long> {

  TransactionKindJakartaEntity findByName(final String name);
  TransactionKindJakartaEntity findByCode(final String name);

}
