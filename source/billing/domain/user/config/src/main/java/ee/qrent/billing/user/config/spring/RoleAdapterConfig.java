package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.user.persistence.adapter.RoleLoadAdapter;
import ee.qrent.billing.user.persistence.adapter.RolePersistenceAdapter;
import ee.qrent.billing.user.persistence.mapper.RoleAdapterMapper;
import ee.qrent.billing.user.persistence.repository.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleAdapterConfig {
  @Bean
  RoleAdapterMapper getRoleAdapterMapper() {
    return new RoleAdapterMapper();
  }

  @Bean
  RoleLoadAdapter getRoleLoadAdapter(
          final RoleRepository repository, final RoleAdapterMapper mapper) {
    return new RoleLoadAdapter(repository, mapper);
  }

  @Bean
  RolePersistenceAdapter getRolePersistenceAdapter(
      final RoleRepository repository,
      final RoleAdapterMapper adapterMapper) {

    return new RolePersistenceAdapter(repository, adapterMapper);
  }
}
