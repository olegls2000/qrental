package ee.qrent.billing.bonus.persistence.repository.spring;

import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BonusCalculationSpringDataRepository
    extends JpaRepository<BonusCalculationJakartaEntity, Long> {

    @Query(
            value =
                    "select qw.id "
                            + "from bonus_calculation bc "
                            + "         LEFT JOIN q_week qw on bc.q_week_id = qw.id "
                            + "order by qw.year desc, qw.number desc "
                            + "limit 1;",
            nativeQuery = true)
    Long getLastCalculationQWeekId();


}
