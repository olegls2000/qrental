package ee.qrent.billing.user.config.spring;

import ee.qrent.billing.user.persistence.adapter.UserAccountLoadAdapter;
import ee.qrent.billing.user.persistence.adapter.UserAccountPersistenceAdapter;
import ee.qrent.billing.user.persistence.mapper.UserAccountAdapterMapper;
import ee.qrent.billing.user.persistence.repository.RoleRepository;
import ee.qrent.billing.user.persistence.repository.UserAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserAccountAdapterConfig {
  @Bean
  UserAccountAdapterMapper getUseerAdapterMapper() {
    return new UserAccountAdapterMapper();
  }

  @Bean
  UserAccountLoadAdapter getUserLoadAdapter(
          final UserAccountRepository repository, final UserAccountAdapterMapper mapper) {
    return new UserAccountLoadAdapter(repository, mapper);
  }

  @Bean
  UserAccountPersistenceAdapter getUserPersistenceAdapter(
      final UserAccountRepository userRepository,
      final RoleRepository roleRepository,
      final UserAccountAdapterMapper userAdapterMapper) {

    return new UserAccountPersistenceAdapter(userRepository, roleRepository, userAdapterMapper);
  }
}
