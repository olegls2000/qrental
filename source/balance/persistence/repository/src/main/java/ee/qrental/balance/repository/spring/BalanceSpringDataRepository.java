package ee.qrental.balance.repository.spring;

import ee.qrental.invoice.entity.jakarta.BalanceJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceSpringDataRepository extends JpaRepository<BalanceJakartaEntity, Long> {}
