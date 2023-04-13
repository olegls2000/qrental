package ee.qrental.transaction.repository.spring;

import ee.qrental.transaction.entity.jakarta.TransactionTypeJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeSpringDataRepository
    extends JpaRepository<TransactionTypeJakartaEntity, Long> {

  TransactionTypeJakartaEntity findByName(final String name);

  List<TransactionTypeJakartaEntity> findAllByNegative(final Boolean negative);
}
