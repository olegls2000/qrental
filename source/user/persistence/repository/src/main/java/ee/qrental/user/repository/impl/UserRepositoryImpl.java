package ee.qrental.user.repository.impl;

import ee.qrental.user.entity.jakarta.UserJakartaEntity;
import ee.qrental.user.repository.spring.UserSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserSpringDataRepository springDataRepository;

  @Override
  public List<UserJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public UserJakartaEntity save(final UserJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public UserJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }
  
  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }
}
