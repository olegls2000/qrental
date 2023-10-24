package ee.qrental.transaction.adapter.repository.rent;


import ee.qrental.transaction.entity.jakarta.rent.RentCalculationResultJakartaEntity;

public interface RentCalculationResultRepository {
  RentCalculationResultJakartaEntity save(final RentCalculationResultJakartaEntity entity);
}
