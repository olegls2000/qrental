package ee.qrent.billing.contract.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.contract.persistence.mapper.AuthorizationAdapterMapper;
import ee.qrent.billing.contract.persistence.repository.AuthorizationRepository;
import ee.qrent.billing.contract.api.out.AuthorizationLoadPort;
import ee.qrent.billing.contract.domain.Authorization;
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

  @Override
  public Authorization loadLatestByDriverId(final Long driverId) {
    final var entity =  repository.getLatestByDriverId(driverId);

    return mapper.mapToDomain(entity);
  }
}
