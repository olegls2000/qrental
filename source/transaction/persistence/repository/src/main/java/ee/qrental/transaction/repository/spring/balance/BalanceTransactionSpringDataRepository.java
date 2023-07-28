package ee.qrental.transaction.repository.spring.balance;

import ee.qrental.transaction.entity.jakarta.balance.BalanceTransactionJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceTransactionSpringDataRepository
    extends JpaRepository<BalanceTransactionJakartaEntity, Long> {
    boolean existsTransactionBalanceJakartaEntityByTransactionId(final Long transactionId);

}
