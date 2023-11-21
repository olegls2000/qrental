package ee.qrental.contract.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.contract.adapter.mapper.AuthorizationAdapterMapper;
import ee.qrental.contract.adapter.repository.AuthorizationRepository;
import ee.qrental.contract.api.out.AuthorizationLoadPort;
import ee.qrental.contract.domain.Authorization;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorizationLoadAdapter implements AuthorizationLoadPort {

  private final AuthorizationRepository repository;
  private final AuthorizationAdapterMapper mapper;

  @Override
  public List<Authorization> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Authorization loadById(final Long id) {
   final var entity =  repository.getReferenceById(id);

   return mapper.mapToDomain(entity);
  }


  @Override
  public Authorization loadByDriverId(Long driverId) {
    return null;
  }
}
