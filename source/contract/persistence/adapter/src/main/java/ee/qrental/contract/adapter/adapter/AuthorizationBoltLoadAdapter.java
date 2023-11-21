package ee.qrental.contract.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.contract.adapter.mapper.AuthorizationBoltAdapterMapper;
import ee.qrental.contract.adapter.repository.AuthorizationBoltRepository;
import ee.qrental.contract.api.out.AuthorizationBoltLoadPort;
import ee.qrental.contract.domain.AuthorizationBolt;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationBoltLoadAdapter implements AuthorizationBoltLoadPort {

  private final AuthorizationBoltRepository repository;
  private final AuthorizationBoltAdapterMapper mapper;

  @Override
  public List<AuthorizationBolt> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public AuthorizationBolt loadById(final Long id) {
   final var entity =  repository.getReferenceById(id);

   return mapper.mapToDomain(entity);
  }


  @Override
  public AuthorizationBolt loadByDriverId(Long driverId) {
    return null;
  }
}
