package ee.qrent.billing.contract.persistence.repository.impl;

import ee.qrent.billing.contract.persistence.repository.AuthorizationRepository;
import ee.qrent.billing.contract.persistence.entity.jakarta.AuthorizationJakartaEntity;
import ee.qrent.billing.contract.persistence.repository.spring.AuthorizationSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationRepositoryImpl implements AuthorizationRepository {

  private final AuthorizationSpringDataRepository springDataRepository;

  @Override
  public List<AuthorizationJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public AuthorizationJakartaEntity save(final AuthorizationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public AuthorizationJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public AuthorizationJakartaEntity getLatestByDriverId(final Long driverId) {
    return springDataRepository.getLastByDriverId(driverId);
  }
}
