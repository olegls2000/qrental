package ee.qrent.billing.user.persistence.adapter;

import ee.qrent.billing.user.api.out.RoleAddPort;
import ee.qrent.billing.user.api.out.RoleDeletePort;
import ee.qrent.billing.user.api.out.RoleUpdatePort;
import ee.qrent.billing.user.domain.Role;
import ee.qrent.billing.user.persistence.mapper.RoleAdapterMapper;
import ee.qrent.billing.user.persistence.repository.RoleRepository;
import ee.qrent.billing.user.persistence.entity.jakarta.RoleJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RolePersistenceAdapter implements RoleAddPort, RoleUpdatePort, RoleDeletePort {

  private final RoleRepository repository;
  private final RoleAdapterMapper adapterMapper;


  @Override
  public Role add(final Role domain) {
    final var userToSave = adapterMapper.mapToEntity(domain);
    final var userSaved = repository.save(userToSave);

    return adapterMapper.mapToDomain(userSaved);
  }

  @Override
  public Role update(final Role domain) {
    final var userId = domain.getId();
    final var entityFromDatabase = repository.getReferenceById(userId);
    final var updatedEntity = updateSavedEntity(entityFromDatabase, domain);

    return adapterMapper.mapToDomain(repository.save(updatedEntity));
  }

  private RoleJakartaEntity updateSavedEntity(
          final RoleJakartaEntity entity, final Role domain) {
    entity.setName(domain.getName());
    entity.setComment(domain.getComment());
    
    return entity;
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
