package ee.qrental.contract.repository.spring;

import ee.qrental.contract.entity.jakarta.ContractJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractSpringDataRepository extends JpaRepository<ContractJakartaEntity, Long> {

    ContractJakartaEntity findByNumber(final String number);

    @Query(
            value =
                    "select ct.* from contract ct "
                            + "where ct.driver_id = :driverId "
                            + "order by ct.created desc limit 1",
            nativeQuery = true)
    ContractJakartaEntity findLatestByDriverId(@Param("driverId") final Long driverId);
}
