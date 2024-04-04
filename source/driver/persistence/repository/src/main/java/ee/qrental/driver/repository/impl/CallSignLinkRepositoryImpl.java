package ee.qrental.driver.repository.impl;

import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.entity.jakarta.CallSignLinkJakartaEntity;
import ee.qrental.driver.repository.spring.CallSignLinkSpringDataRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkRepositoryImpl implements CallSignLinkRepository {

  private final CallSignLinkSpringDataRepository springDataRepository;

  @Override
  public CallSignLinkJakartaEntity save(CallSignLinkJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public CallSignLinkJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public List<CallSignLinkJakartaEntity> findAllByCallSignId(Long callSignId) {
    return springDataRepository.findAllByCallSignId(callSignId);
  }

  @Override
  public List<CallSignLinkJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public CallSignLinkJakartaEntity findOneByDateEndIsNullAndCallSignId(final Long callSignId) {
    return springDataRepository.findOneByDateEndIsNullAndCallSignId(callSignId);
  }

  @Override
  public CallSignLinkJakartaEntity findOneByDateEndIsNullAndDriverId(final Long driverId) {
    return springDataRepository.findOneByDateEndIsNullAndDriverId(driverId);
  }

  @Override
  public CallSignLinkJakartaEntity findActiveByDriverIdAndNowDate(
      final Long driverId, final LocalDate nowDate) {
    return springDataRepository.findActiveByDriverIdAndNowDate(driverId, nowDate);
  }

  @Override
  public List<CallSignLinkJakartaEntity> findActiveByDate(final LocalDate date) {
    return springDataRepository.findActiveByDate(date);
  }

  @Override
  public Long findCountActiveByDate(final LocalDate date) {
    return springDataRepository.findCountActiveByDate(date);
  }

  @Override
  public List<CallSignLinkJakartaEntity> findClosedByDate(final LocalDate date) {
    return springDataRepository.findClosedByDate(date);
  }

  @Override
  public Long findCountClosedByDate(final LocalDate date) {
    return springDataRepository.findCountClosedByDate(date);
  }

  @Override
  public List<CallSignLinkJakartaEntity> findAllByDriverId(final Long driverId) {
    return springDataRepository.findAllByDriverId(driverId);
  }
}
