package ee.qrental.callsign.repository.impl;

import ee.qrental.callsign.adapter.repository.CallSignRepository;
import ee.qrental.callsign.entity.jakarta.CallSignJakartaEntity;
import ee.qrental.callsign.repository.spring.CallSignSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignRepositoryImpl implements CallSignRepository {

  private final CallSignSpringDataRepository springDataRepository;

  @Override
  public List<CallSignJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public CallSignJakartaEntity save(final CallSignJakartaEntity jpaEntity) {
    return springDataRepository.save(jpaEntity);
  }

  @Override
  public CallSignJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public CallSignJakartaEntity findByCallSign(final Integer callSign) {
    return springDataRepository.findByCallSign(callSign);
  }
}
