package ee.qrent.billing.transaction.persistence.repository.spring.balance;

import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceTransactionJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceTransactionSpringDataRepository
    extends JpaRepository<BalanceTransactionJakartaEntity, Long> {
    boolean existsTransactionBalanceJakartaEntityByTransactionId(final Long transactionId);

}
