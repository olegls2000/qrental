package ee.qrent.billing.driver.persistence.repository.impl;

import ee.qrent.billing.driver.persistence.repository.FirmLinkRepository;
import ee.qrent.billing.driver.persistence.entity.jakarta.FirmLinkJakartaEntity;
import ee.qrent.billing.driver.persistence.repository.spring.FirmLinkSpringDataRepository;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLinkRepositoryImpl implements FirmLinkRepository {

  private final FirmLinkSpringDataRepository springDataRepository;

  @Override
  public FirmLinkJakartaEntity save(FirmLinkJakartaEntity entity) {

    return springDataRepository.save(entity);
  }

  @Override
  public FirmLinkJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public List<FirmLinkJakartaEntity> findAll() {

    return springDataRepository.findAll();
  }

  @Override
  public FirmLinkJakartaEntity findOneByDriverIdAndDate(final Long driverId, final LocalDate date) {

    return springDataRepository.findOneByDriverIdAndDate(driverId, date);
  }
}
