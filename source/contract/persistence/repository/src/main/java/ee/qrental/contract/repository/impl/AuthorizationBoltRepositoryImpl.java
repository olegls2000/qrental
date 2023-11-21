package ee.qrental.contract.repository.impl;

import ee.qrental.contract.adapter.repository.AuthorizationBoltRepository;
import ee.qrental.contract.entity.jakarta.AuthorizationBoltJakartaEntity;
import ee.qrental.contract.repository.spring.AuthorizationBoltSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationBoltRepositoryImpl implements AuthorizationBoltRepository {

  private final AuthorizationBoltSpringDataRepository springDataRepository;

  @Override
  public List<AuthorizationBoltJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public AuthorizationBoltJakartaEntity save(final AuthorizationBoltJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public AuthorizationBoltJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }
}
