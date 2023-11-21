package ee.qrental.contract.adapter.adapter;


import ee.qrental.contract.adapter.mapper.AuthorizationAdapterMapper;
import ee.qrental.contract.adapter.repository.AuthorizationRepository;
import ee.qrental.contract.api.out.AuthorizationBoltAddPort;
import ee.qrental.contract.api.out.AuthorizationBoltDeletePort;
import ee.qrental.contract.api.out.AuthorizationBoltUpdatePort;
import ee.qrental.contract.domain.Authorization;
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
