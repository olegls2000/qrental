package ee.qrental.bonus.repository.spring;

import ee.qrental.bonus.entity.jakarta.ObligationCalculationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ObligationCalculationSpringDataRepository
    extends JpaRepository<ObligationCalculationJakartaEntity, Long> {

    @Query(
            value =
                    "select qw.id "
                            + "from obligation_calculation oc "
                            + "         LEFT JOIN q_week qw on oc.q_week_id = qw.id "
                            + "order by qw.year desc, qw.number desc "
                            + "limit 1;",
            nativeQuery = true)
    Long getLastCalculationQWeekId();


}
