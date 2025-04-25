package ee.qrent.billing.contract.persistence.repository.spring;

import ee.qrent.billing.contract.persistence.entity.jakarta.AuthorizationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorizationSpringDataRepository
    extends JpaRepository<AuthorizationJakartaEntity, Long> {

  @Query(
      value =
          "select ab.* from authorization_bolt ab where ab.driver_id = :driverId "
              + "order by ab.created desc limit 1;",
      nativeQuery = true)
  AuthorizationJakartaEntity getLastByDriverId(@Param("driverId") final Long driverId);
}
