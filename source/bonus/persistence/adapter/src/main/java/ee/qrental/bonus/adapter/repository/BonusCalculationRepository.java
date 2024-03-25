package ee.qrental.bonus.adapter.repository;

import ee.qrental.bonus.entity.jakarta.BonusCalculationJakartaEntity;
import java.util.List;

public interface BonusCalculationRepository {

    BonusCalculationJakartaEntity save(final BonusCalculationJakartaEntity entity);

    List<BonusCalculationJakartaEntity> findAll();

    BonusCalculationJakartaEntity getReferenceById(final Long id);

    Long getLastCalculationQWeekId();
}
