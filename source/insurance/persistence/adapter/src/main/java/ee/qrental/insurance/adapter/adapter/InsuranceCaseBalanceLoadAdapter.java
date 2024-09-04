package ee.qrental.insurance.adapter.adapter;

import ee.qrental.insurance.adapter.mapper.InsuranceCaseBalanceAdapterMapper;
import ee.qrental.insurance.adapter.repository.InsuranceCaseBalanceRepository;
import ee.qrental.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class InsuranceCaseBalanceLoadAdapter implements InsuranceCaseBalanceLoadPort {

  private final InsuranceCaseBalanceRepository repository;
  private final InsuranceCaseBalanceAdapterMapper mapper;

  @Override
  public InsuranceCaseBalance loadLatestByInsuranceCaseId(final Long insuranceCseId) {
    final var entity = repository.findLatestByInsuranceCaseId(insuranceCseId);

    return mapper.mapToDomain(entity);
  }

  @Override
  public InsuranceCaseBalance loadByInsuranceCaseIdAndQWeekId(
      final Long insuranceCseId, final Long qWeekId) {
    final var entity = repository.findByInsuranceCaseIdAndQWeekId(insuranceCseId, qWeekId);

    return mapper.mapToDomain(entity);
  }

  @Override
  public List<InsuranceCaseBalance> loadAllByInsuranceCseId(Long insuranceCseId) {
    return repository.findAllByInsuranceCaseId(insuranceCseId).stream()
        .map(entity -> mapper.mapToDomain(entity))
        .toList();
  }

  @Override
  public List<InsuranceCaseBalance> loadAllByQWeekIdAndDriverId(
      final Long qWeekId, final Long driverId) {
    return repository.findAllByQWeekIdAndDriverId(qWeekId, driverId).stream()
        .map(entity -> mapper.mapToDomain(entity))
        .toList();
  }
}
