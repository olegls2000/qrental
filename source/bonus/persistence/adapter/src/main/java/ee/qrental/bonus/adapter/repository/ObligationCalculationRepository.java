package ee.qrental.bonus.adapter.repository;

import ee.qrental.bonus.entity.jakarta.ObligationCalculationJakartaEntity;
import java.util.List;

public interface ObligationCalculationRepository {

    ObligationCalculationJakartaEntity save(final ObligationCalculationJakartaEntity entity);

    List<ObligationCalculationJakartaEntity> findAll();

    ObligationCalculationJakartaEntity getReferenceById(final Long id);

    Long getLastCalculationQWeekId();
}
