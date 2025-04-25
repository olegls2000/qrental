package ee.qrent.billing.contract.persistence.adapter;


import ee.qrent.billing.contract.persistence.mapper.AuthorizationAdapterMapper;
import ee.qrent.billing.contract.persistence.repository.AuthorizationRepository;
import ee.qrent.billing.contract.api.out.AuthorizationBoltAddPort;
import ee.qrent.billing.contract.api.out.AuthorizationBoltDeletePort;
import ee.qrent.billing.contract.api.out.AuthorizationBoltUpdatePort;
import ee.qrent.billing.contract.domain.Authorization;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationPersistenceAdapter
    implements AuthorizationBoltAddPort, AuthorizationBoltUpdatePort, AuthorizationBoltDeletePort {

  private final AuthorizationRepository repository;
  private final AuthorizationAdapterMapper mapper;

  @Override
  public Authorization add(final Authorization domain) {
    final var savedAuthorizationBoltEntity = repository.save(mapper.mapToEntity(domain));

    return mapper.mapToDomain(savedAuthorizationBoltEntity);
  }

  @Override
  public Authorization update(final Authorization domain) {
    repository.save(mapper.mapToEntity(domain));

    return domain;
  }

  @Override
  public void delete(final Long id) {
    repository.deleteById(id);
  }
}
