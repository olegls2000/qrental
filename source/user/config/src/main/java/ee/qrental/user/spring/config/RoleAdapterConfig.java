package ee.qrental.user.spring.config;

import ee.qrental.user.adapter.adapter.RoleLoadAdapter;
import ee.qrental.user.adapter.adapter.RolePersistenceAdapter;
import ee.qrental.user.adapter.mapper.RoleAdapterMapper;
import ee.qrental.user.adapter.repository.RoleRepository;
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
