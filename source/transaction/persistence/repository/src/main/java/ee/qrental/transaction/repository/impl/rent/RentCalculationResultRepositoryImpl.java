package ee.qrental.transaction.repository.impl.rent;

import ee.qrental.transaction.adapter.repository.rent.RentCalculationResultRepository;
import ee.qrental.transaction.entity.jakarta.rent.RentCalculationResultJakartaEntity;
import ee.qrental.transaction.repository.spring.rent.RentCalculationResultSpringDataRepository;
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
