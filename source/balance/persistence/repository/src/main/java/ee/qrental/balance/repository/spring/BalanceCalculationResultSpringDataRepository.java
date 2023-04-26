package ee.qrental.balance.repository.spring;

import ee.qrental.invoice.entity.jakarta.BalanceCalculationResultJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceCalculationResultSpringDataRepository
    extends JpaRepository<BalanceCalculationResultJakartaEntity, Long> {}
