package ee.qrent.billing.constant.config.spring;

import ee.qrent.billing.constant.persistence.repository.ConstantRepository;
import ee.qrent.billing.constant.repository.impl.ConstantRepositoryImpl;
import ee.qrent.billing.constant.repository.spring.ConstantSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstantRepositoryConfig {

  @Bean
  ConstantRepository getConstantRepository(final ConstantSpringDataRepository springDataRepository) {
    return new ConstantRepositoryImpl(springDataRepository);
  }
}
