package ee.qrental.contract.repository.impl;

import ee.qrental.contract.adapter.repository.AuthorizationRepository;
import ee.qrental.contract.entity.jakarta.AuthorizationJakartaEntity;
import ee.qrental.contract.repository.spring.AuthorizationSpringDataRepository;
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
}
