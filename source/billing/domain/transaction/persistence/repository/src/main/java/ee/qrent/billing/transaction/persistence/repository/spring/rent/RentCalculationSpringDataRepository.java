package ee.qrent.billing.transaction.persistence.repository.spring.rent;

import ee.qrent.billing.transaction.persistence.entity.jakarta.rent.RentCalculationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RentCalculationSpringDataRepository
    extends JpaRepository<RentCalculationJakartaEntity, Long> {

  @Query(
      value =
          "select qw.id "
              + "from rent_calculation rc "
              + "         LEFT JOIN q_week qw on rc.q_week_id = qw.id "
              + "order by qw.year desc, qw.number desc "
              + "limit 1;",
      nativeQuery = true)
  Long getLastCalculationQWeekId();

}
