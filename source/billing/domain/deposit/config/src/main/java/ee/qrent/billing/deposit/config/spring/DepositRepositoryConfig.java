package ee.qrent.billing.deposit.config.spring;

import ee.qrent.billing.deposit.persistence.repository.DepositRepository;
import ee.qrent.billing.deposit.persistence.repository.impl.DepositRepositoryImpl;
import ee.qrent.billing.deposit.persistence.repository.spring.DepositSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepositRepositoryConfig {

  @Bean
  DepositRepository getDepositRepositoryImpl(
      final DepositSpringDataRepository springDataRepository) {

    return new DepositRepositoryImpl(springDataRepository);
  }
}
