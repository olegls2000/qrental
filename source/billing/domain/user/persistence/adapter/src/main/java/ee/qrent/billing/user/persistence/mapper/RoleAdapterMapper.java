package ee.qrent.billing.user.persistence.mapper;

import ee.qrent.billing.user.domain.Role;
import ee.qrent.billing.user.persistence.entity.jakarta.RoleJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleAdapterMapper {
  
  public Role mapToDomain(final RoleJakartaEntity entity) {
    if(entity == null){
      return null;
    }

    return Role.builder()
        .id(entity.getId())
        .name(entity.getName())
        .comment(entity.getComment())
        .build();
  }

  public RoleJakartaEntity mapToEntity(final Role domain) {

    return RoleJakartaEntity.builder()
        .id(domain.getId())
        .name(domain.getName())
        .comment(domain.getComment())
        .build();
  }
}
