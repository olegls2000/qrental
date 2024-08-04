package ee.qrental.insurance.repository.impl;

import ee.qrental.insurance.adapter.repository.InsuranceCaseRepository;
import ee.qrental.insurance.entity.jakarta.InsuranceCaseJakartaEntity;
import ee.qrental.insurance.repository.spring.InsuranceCaseSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseRepositoryImpl implements InsuranceCaseRepository {

  private final InsuranceCaseSpringDataRepository springDataRepository;

  @Override
  public List<InsuranceCaseJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public InsuranceCaseJakartaEntity save(final InsuranceCaseJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public InsuranceCaseJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(final Long id) {
    springDataRepository.deleteById(id);
  }
}
