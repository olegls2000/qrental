package ee.qrental.balance.repository.spring;

import ee.qrental.invoice.entity.jakarta.BalanceTransactionJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceTransactionSpringDataRepository
    extends JpaRepository<BalanceTransactionJakartaEntity, Long> {}
