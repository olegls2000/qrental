package ee.qrent.billing.transaction.persistence.repository.rent;


import ee.qrent.billing.transaction.persistence.entity.jakarta.rent.RentCalculationResultJakartaEntity;

public interface RentCalculationResultRepository {
  RentCalculationResultJakartaEntity save(final RentCalculationResultJakartaEntity entity);
}
