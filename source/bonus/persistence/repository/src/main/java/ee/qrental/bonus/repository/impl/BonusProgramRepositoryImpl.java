package ee.qrental.bonus.repository.impl;

import ee.qrental.bonus.adapter.repository.BonusProgramRepository;
import ee.qrental.bonus.entity.jakarta.BonusProgramJakartaEntity;
import ee.qrental.bonus.repository.spring.BonusProgramSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusProgramRepositoryImpl implements BonusProgramRepository {

  private final BonusProgramSpringDataRepository springDataRepository;

  @Override
  public List<BonusProgramJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public List<BonusProgramJakartaEntity> findAllByActive(boolean active) {
    return null;
  }

  @Override
  public BonusProgramJakartaEntity save(final BonusProgramJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public BonusProgramJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }
}
