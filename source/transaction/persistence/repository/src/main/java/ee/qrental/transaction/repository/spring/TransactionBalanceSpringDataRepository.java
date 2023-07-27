package ee.qrental.transaction.repository.spring;

import ee.qrental.transaction.entity.jakarta.TransactionBalanceJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionBalanceSpringDataRepository
    extends JpaRepository<TransactionBalanceJakartaEntity, Long> {

  boolean existsTransactionBalanceJakartaEntityByTransactionId(final Long transactionId);
}
