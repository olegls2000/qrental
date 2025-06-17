package ee.qrent.billing.bolt.persistence.repository.impl;

import ee.qrent.billing.bolt.persistence.entity.jakarta.BoltStatisticsJakartaEntity;
import ee.qrent.billing.bolt.persistence.repository.BoltStatisticsRepository;
import ee.qrent.billing.bolt.persistence.repository.spring.BoltStatisticsSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BoltStatisticsRepositoryImpl implements BoltStatisticsRepository {

  private final BoltStatisticsSpringDataRepository springDataRepository;

  @Override
  public List<BoltStatisticsJakartaEntity> findAll() {

    return springDataRepository.findAllWithoutData();
  }

  @Override
  public BoltStatisticsJakartaEntity save(final BoltStatisticsJakartaEntity entity) {
    springDataRepository.saveNatively(entity);
    final var savedEntity =
        springDataRepository.findByRegionAndYearAndMonth(
            entity.getRegion(), entity.getYear(), entity.getMonth());

    return savedEntity;
  }

  @Override
  public BoltStatisticsJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.findOneWithoutData(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public byte[] getDataById(final Long id) {

    return springDataRepository.findDataById(id);
  }
}
