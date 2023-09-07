package ee.qrental.user.repository.impl;

import ee.qrental.user.adapter.repository.RoleRepository;
import ee.qrental.user.entity.jakarta.RoleJakartaEntity;
import ee.qrental.user.repository.spring.RoleSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

  private final RoleSpringDataRepository springDataRepository;

  @Override
  public List<RoleJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public RoleJakartaEntity save(final RoleJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public RoleJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }
  
  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public RoleJakartaEntity findByName(final String name) {
    return springDataRepository.findByName(name);
  }

  @Override
  public List<RoleJakartaEntity> findAllById(Iterable<Long> ids) {
    return springDataRepository.findAllById(ids);
  }
}
