package ee.qrent.billing.insurance.repository.impl;

import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseRepository;
import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCaseJakartaEntity;
import ee.qrent.billing.insurance.repository.spring.InsuranceCaseSpringDataRepository;
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
  public List<InsuranceCaseJakartaEntity> findActiveByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {
    return springDataRepository.findAllByActiveIsTrueAndDriverIdAndQWeekId(driverId, qWeekId);
  }

  @Override
  public List<InsuranceCaseJakartaEntity> findActiveByQWeekId(final Long qWeekId) {
    return springDataRepository.findAllByActiveIsTrueAndQWeekId(qWeekId);
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
  public List<InsuranceCaseJakartaEntity> findActive() {
    return springDataRepository.findActive();
  }

  @Override
  public List<InsuranceCaseJakartaEntity> findClosed() {
    return springDataRepository.findClosed();
  }

  @Override
  public Long findCountActive() {
    return springDataRepository.findCountActive();
  }

  @Override
  public Long findCountClosed() {
    return springDataRepository.findCountClosed();
  }

  @Override
  public List<InsuranceCaseJakartaEntity> findActiveByDriverId(final Long driverId) {
    return springDataRepository.findActiveByDriverId(driverId);
  }

  @Override
  public List<Long> findPaymentTransactionIdsByInsuranceCaseId(Long insuranceCaseId) {
    return  springDataRepository.findPaymentTransactionIdsByInsuranceCaseId(insuranceCaseId);
  }
}
