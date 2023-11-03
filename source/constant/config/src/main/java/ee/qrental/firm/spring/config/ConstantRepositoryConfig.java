package ee.qrental.firm.spring.config;

import ee.qrental.constant.adapter.repository.ConstantRepository;
import ee.qrental.constant.repository.impl.ConstantRepositoryImpl;
import ee.qrental.constant.repository.spring.ConstantSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstantRepositoryConfig {

  @Bean
  ConstantRepository getConstantRepository(final ConstantSpringDataRepository springDataRepository) {
    return new ConstantRepositoryImpl(springDataRepository);
  }
}
