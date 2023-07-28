package ee.qrental.transaction.repository.spring.balance;

import ee.qrental.transaction.entity.jakarta.balance.BalanceCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceCalculationResultSpringDataRepository
    extends JpaRepository<BalanceCalculationResultJakartaEntity, Long> {}
