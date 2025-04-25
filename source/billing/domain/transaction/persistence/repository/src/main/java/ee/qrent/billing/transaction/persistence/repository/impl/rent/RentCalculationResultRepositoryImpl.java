package ee.qrent.billing.transaction.persistence.repository.impl.rent;

import ee.qrent.billing.transaction.persistence.repository.rent.RentCalculationResultRepository;
import ee.qrent.billing.transaction.persistence.entity.jakarta.rent.RentCalculationResultJakartaEntity;
import ee.qrent.billing.transaction.persistence.repository.spring.rent.RentCalculationResultSpringDataRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationResultRepositoryImpl implements RentCalculationResultRepository {

  private final RentCalculationResultSpringDataRepository springDataRepository;

  @Override
  public RentCalculationResultJakartaEntity save(
      final RentCalculationResultJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
