package ee.qrent.billing.insurance.persistence.adapter;

import ee.qrent.billing.insurance.persistence.mapper.InsuranceCaseAdapterMapper;
import ee.qrent.billing.insurance.persistence.repository.InsuranceCaseRepository;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseLoadAdapter implements InsuranceCaseLoadPort {

  private final InsuranceCaseRepository repository;
  private final InsuranceCaseAdapterMapper mapper;

  @Override
  public List<InsuranceCase> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public InsuranceCase loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<InsuranceCase> loadActiveByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {
    return repository.findActiveByDriverIdAndQWeekId(driverId, qWeekId).stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<InsuranceCase> loadActiveByQWeekId(final Long qWeekId) {
    return repository.findActiveByQWeekId(qWeekId).stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public List<InsuranceCase> loadAllActive() {
    return repository.findActive().stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public List<InsuranceCase> loadAlClosed() {
    return repository.findClosed().stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public Long loadCountActive() {
    return repository.findCountActive();
  }

  @Override
  public Long loadCountClosed() {
    return repository.findCountClosed();
  }

  @Override
  public List<InsuranceCase> loadAllActiveByDriverId(final Long driverId) {
    return repository.findActiveByDriverId(driverId).stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public List<Long> loadPaymentTransactionIdsByInsuranceCaseId(Long insuranceCaseId) {
    return repository.findPaymentTransactionIdsByInsuranceCaseId(insuranceCaseId);
  }
}
