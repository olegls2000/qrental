package ee.qrental.insurance.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.insurance.adapter.mapper.InsuranceCaseAdapterMapper;
import ee.qrental.insurance.adapter.repository.InsuranceCaseRepository;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.domain.InsuranceCase;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseLoadAdapter implements InsuranceCaseLoadPort {

  private final InsuranceCaseRepository repository;
  private final InsuranceCaseAdapterMapper mapper;

  @Override
  public List<InsuranceCase> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
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
        .collect(toList());
  }

  @Override
  public List<InsuranceCase> loadActiveByQWeekId(final Long qWeekId) {
    return repository.findActiveByQWeekId(qWeekId).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }
}
