package ee.qrental.callsign.repository.impl;

import ee.qrental.callsign.adapter.repository.CallSignLinkRepository;
import ee.qrental.callsign.entity.jakarta.CallSignLinkJakartaEntity;
import ee.qrental.callsign.repository.spring.CallSignLinkSpringDataRepository;
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
  public List<CallSignLinkJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public List<CallSignLinkJakartaEntity> findAllByDateEndIsNull() {
    return springDataRepository.findAllByDateEndIsNull();
  }

  @Override
  public CallSignLinkJakartaEntity findByDriverId(final Long driverId) {
    return springDataRepository.findFirstByDriverId(driverId);
  }

  @Override
  public Integer findCallSignByDriverId(final Long driverId) {
    return springDataRepository.getCallSignByDriverId(driverId);
  }
}
