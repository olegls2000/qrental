package ee.qrental.contract.repository.spring;

import ee.qrental.contract.entity.jakarta.ContractJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractSpringDataRepository extends JpaRepository<ContractJakartaEntity, Long> {}
