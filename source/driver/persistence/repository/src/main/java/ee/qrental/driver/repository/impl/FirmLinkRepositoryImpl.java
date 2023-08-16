package ee.qrental.driver.repository.impl;

import ee.qrental.driver.adapter.repository.FirmLinkRepository;
import ee.qrental.driver.entity.jakarta.FirmLinkJakartaEntity;
import ee.qrental.driver.repository.spring.FirmLinkSpringDataRepository;

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
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public FirmLinkJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public FirmLinkJakartaEntity findOneByDriverIdAndRequiredDate(
          final Long driverId, final LocalDate requiredDate) {
    return springDataRepository.findOneByDriverIdAndRequiredDate(driverId, requiredDate);
  }

  @Override
  public List<FirmLinkJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }
}
