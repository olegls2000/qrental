package ee.qrent.billing.transaction.persistence.repository.impl.rent;

import ee.qrent.billing.transaction.persistence.repository.rent.RentCalculationRepository;
import ee.qrent.billing.transaction.persistence.entity.jakarta.rent.RentCalculationJakartaEntity;
import ee.qrent.billing.transaction.persistence.repository.spring.rent.RentCalculationSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationRepositoryImpl implements RentCalculationRepository {

  private final RentCalculationSpringDataRepository springDataRepository;

  @Override
  public RentCalculationJakartaEntity save(final RentCalculationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public List<RentCalculationJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public RentCalculationJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public Long getLastCalculationQWeekId() {
    return springDataRepository.getLastCalculationQWeekId();
  }
}
