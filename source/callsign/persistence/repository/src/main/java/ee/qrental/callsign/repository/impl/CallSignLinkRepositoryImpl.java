package ee.qrental.callsign.repository.impl;

import ee.qrental.callsign.adapter.repository.CallSignLinkRepository;
import ee.qrental.callsign.entity.jakarta.CallSignLinkJakartaEntity;
import ee.qrental.callsign.repository.spring.CallSignLinkSpringDataRepository;
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
  public CallSignLinkJakartaEntity findOneByDriverIdAndDate(
      final Long driverId, final LocalDate date) {
    return springDataRepository.findOneByDriverIdAndDate(driverId, date);
  }
}
