package ee.qrent.billing.user.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.user.domain.Role;
import ee.qrent.billing.user.persistence.mapper.RoleAdapterMapper;
import ee.qrent.billing.user.persistence.repository.RoleRepository;
import ee.qrent.billing.user.api.out.RoleLoadPort;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleLoadAdapter implements RoleLoadPort {

  private final RoleRepository repository;
  private final RoleAdapterMapper mapper;

  @Override
  public List<Role> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Role loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public Role loadByName(final String name) {
    return mapper.mapToDomain(repository.findByName(name));
  }
}
