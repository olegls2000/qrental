package ee.qrent.billing.bolt.persistence.repository.impl;

import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltOrdersCountJakartaEntity;
import ee.qrent.billing.bolt.persistence.repository.BoltOrdersCountRepository;
import ee.qrent.billing.bolt.persistence.repository.spring.BoltOrdersCountSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BoltOrdersCountRepositoryImpl implements BoltOrdersCountRepository {

  private final BoltOrdersCountSpringDataRepository springDataRepository;

  @Override
  public BoltOrdersCountJakartaEntity save(final BoltOrdersCountJakartaEntity entity) {

    return springDataRepository.save(entity);
  }

  @Override
  public BoltOrdersCountJakartaEntity getByDriverIdAndQWeekId(Long driverId, Long qWeekId) {

    return springDataRepository.findOneByDriverIdAndQWeekId(driverId, qWeekId);
  }

  @Override
  public List<BoltOrdersCountJakartaEntity> getAllByYearAndMonth(
      final Integer year, Integer month) {

    return springDataRepository.findAllByYearAndMonth(year, month);
  }
}
