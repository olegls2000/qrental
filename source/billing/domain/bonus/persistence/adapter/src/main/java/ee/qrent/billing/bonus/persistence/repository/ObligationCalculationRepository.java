package ee.qrent.billing.bonus.persistence.repository;

import ee.qrent.billing.bonus.persistence.entity.jakarta.ObligationCalculationJakartaEntity;
import java.util.List;

public interface ObligationCalculationRepository {

    ObligationCalculationJakartaEntity save(final ObligationCalculationJakartaEntity entity);

    List<ObligationCalculationJakartaEntity> findAll();

    ObligationCalculationJakartaEntity getReferenceById(final Long id);

    Long getLastCalculationQWeekId();
}
