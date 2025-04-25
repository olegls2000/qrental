package ee.qrent.billing.contract.persistence.repository.impl;

import ee.qrent.billing.contract.persistence.repository.AbsenceRepository;
import ee.qrent.billing.contract.persistence.entity.jakarta.AbsenceJakartaEntity;
import ee.qrent.billing.contract.persistence.repository.spring.AbsenceSpringDataRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class AbsenceRepositoryImpl implements AbsenceRepository {
  private final AbsenceSpringDataRepository springDataRepository;

  @Override
  public AbsenceJakartaEntity findById(Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public List<AbsenceJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public AbsenceJakartaEntity save(final AbsenceJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }

  @Override
  public List<AbsenceJakartaEntity> findByDriverIdAndDateStartAndDateEnd(
      Long driverId, LocalDate dateStart, LocalDate dateEnd) {
    return springDataRepository.findByDriverIdAndDateStartAndDateEnd(driverId, dateStart, dateEnd);
  }

  @Override
  public List<AbsenceJakartaEntity> findByDriverIdAndDateStart(
      final Long driverId, final LocalDate dateStart) {
    return springDataRepository.findByDriverIdAndDateStart(driverId, dateStart);
  }
}
