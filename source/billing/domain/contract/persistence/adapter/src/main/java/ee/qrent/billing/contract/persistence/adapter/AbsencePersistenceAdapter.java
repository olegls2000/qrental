package ee.qrent.billing.contract.persistence.adapter;

import ee.qrent.billing.contract.api.out.AbsenceAddPort;
import ee.qrent.billing.contract.api.out.AbsenceDeletePort;
import ee.qrent.billing.contract.api.out.AbsenceUpdatePort;
import ee.qrent.billing.contract.persistence.mapper.AbsenceAdapterMapper;
import ee.qrent.billing.contract.persistence.repository.AbsenceRepository;
import ee.qrent.billing.contract.domain.Absence;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AbsencePersistenceAdapter
    implements AbsenceAddPort, AbsenceUpdatePort, AbsenceDeletePort {

  private final AbsenceRepository repository;
  private final AbsenceAdapterMapper mapper;

  @Override
  public Absence add(final Absence domain) {
    final var savedEntity = repository.save(mapper.mapToEntity(domain));

    return mapper.mapToDomain(savedEntity);
  }

  @Override
  public Absence update(final Absence domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(final Long id) {
    repository.deleteById(id);
  }
}
