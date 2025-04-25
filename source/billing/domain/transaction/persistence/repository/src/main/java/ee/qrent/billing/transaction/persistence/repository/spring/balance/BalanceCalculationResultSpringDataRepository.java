package ee.qrent.billing.transaction.persistence.repository.spring.balance;

import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceCalculationResultSpringDataRepository
    extends JpaRepository<BalanceCalculationResultJakartaEntity, Long> {}
