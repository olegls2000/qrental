package ee.qrental.contract.adapter.adapter;


import ee.qrental.contract.adapter.mapper.AuthorizationBoltAdapterMapper;
import ee.qrental.contract.adapter.repository.AuthorizationBoltRepository;
import ee.qrental.contract.api.out.AuthorizationBoltAddPort;
import ee.qrental.contract.api.out.AuthorizationBoltDeletePort;
import ee.qrental.contract.api.out.AuthorizationBoltUpdatePort;
import ee.qrental.contract.domain.AuthorizationBolt;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationBoltPersistenceAdapter
    implements AuthorizationBoltAddPort, AuthorizationBoltUpdatePort, AuthorizationBoltDeletePort {

  private final AuthorizationBoltRepository repository;
  private final AuthorizationBoltAdapterMapper mapper;

  @Override
  public AuthorizationBolt add(final AuthorizationBolt domain) {
    final var savedAuthorizationBoltEntity = repository.save(mapper.mapToEntity(domain));

    return mapper.mapToDomain(savedAuthorizationBoltEntity);
  }

  @Override
  public AuthorizationBolt update(final AuthorizationBolt domain) {
    repository.save(mapper.mapToEntity(domain));

    return domain;
  }

  @Override
  public void delete(final Long id) {
    repository.deleteById(id);
  }
}
