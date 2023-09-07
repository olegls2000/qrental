package ee.qrental.user.spring.config;

import ee.qrental.user.adapter.adapter.UserAccountLoadAdapter;
import ee.qrental.user.adapter.adapter.UserAccountPersistenceAdapter;
import ee.qrental.user.adapter.mapper.UserAccountAdapterMapper;
import ee.qrental.user.adapter.repository.RoleRepository;
import ee.qrental.user.adapter.repository.UserAccountRepository;
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
