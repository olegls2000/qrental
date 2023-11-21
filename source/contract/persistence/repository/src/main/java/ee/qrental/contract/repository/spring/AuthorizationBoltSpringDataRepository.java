package ee.qrental.contract.repository.spring;

import ee.qrental.contract.entity.jakarta.AuthorizationBoltJakartaEntity;
import ee.qrental.contract.entity.jakarta.ContractJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorizationBoltSpringDataRepository
    extends JpaRepository<AuthorizationBoltJakartaEntity, Long> {}
