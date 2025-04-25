package ee.qrent.billing.bonus.persistence.repository;

import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationJakartaEntity;
import java.util.List;

public interface BonusCalculationRepository {

    BonusCalculationJakartaEntity save(final BonusCalculationJakartaEntity entity);

    List<BonusCalculationJakartaEntity> findAll();

    BonusCalculationJakartaEntity getReferenceById(final Long id);

    Long getLastCalculationQWeekId();
}
