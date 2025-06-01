package ee.qrent.billing.deposit.persistence.repository.spring;

import ee.qrent.billing.deposit.persistence.entity.jakarta.DepositJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositSpringDataRepository
        extends JpaRepository<DepositJakartaEntity, Long> {

    List<DepositJakartaEntity> findAllByDriverId(Long driverId);
}
