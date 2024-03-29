package ee.qrental.transaction.repository.spring.type;

import ee.qrental.transaction.entity.jakarta.type.TransactionTypeJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeSpringDataRepository
    extends JpaRepository<TransactionTypeJakartaEntity, Long> {

  TransactionTypeJakartaEntity findByName(final String name);

  List<TransactionTypeJakartaEntity> findAllByNegative(final Boolean negative);
}
