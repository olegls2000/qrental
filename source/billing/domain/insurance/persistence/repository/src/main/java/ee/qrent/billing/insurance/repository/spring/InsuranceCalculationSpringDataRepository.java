package ee.qrent.billing.insurance.repository.spring;

import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCalculationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InsuranceCalculationSpringDataRepository
    extends JpaRepository<InsuranceCalculationJakartaEntity, Long> {

  @Query(
      value =
          "select qw.id "
              + "from insurance_calculation ic "
              + "         LEFT JOIN q_week qw on ic.q_week_id = qw.id "
              + "order by qw.year desc, qw.number desc "
              + "limit 1;",
      nativeQuery = true)
  Long getLastCalculationQWeekId();
}
