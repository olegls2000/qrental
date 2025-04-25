package ee.qrent.billing.transaction.persistence.repository.rent;


import ee.qrent.billing.transaction.persistence.entity.jakarta.rent.RentCalculationJakartaEntity;

import java.util.List;

public interface RentCalculationRepository {
  RentCalculationJakartaEntity save(final RentCalculationJakartaEntity entity);

  List<RentCalculationJakartaEntity> findAll();

  RentCalculationJakartaEntity getReferenceById(final Long id);

  Long getLastCalculationQWeekId();
}
