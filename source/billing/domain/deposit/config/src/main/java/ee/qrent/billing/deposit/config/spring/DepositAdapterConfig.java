package ee.qrent.billing.deposit.config.spring;

import ee.qrent.billing.deposit.persistence.adapter.DepositLoadAdapter;
import ee.qrent.billing.deposit.persistence.adapter.DepositPersistenceAdapter;
import ee.qrent.billing.deposit.persistence.mapper.DepositAdapterMapper;
import ee.qrent.billing.deposit.persistence.repository.DepositRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepositAdapterConfig {
  @Bean
  DepositAdapterMapper getDepositAdapterMapper() {

    return new DepositAdapterMapper();
  }

  @Bean
  DepositLoadAdapter getDepositLoadAdapter(
      final DepositRepository repository, final DepositAdapterMapper mapper) {

    return new DepositLoadAdapter(repository, mapper);
  }

  @Bean
  DepositPersistenceAdapter getDepositPersistenceAdapter(
      final DepositRepository repository, final DepositAdapterMapper mapper) {

    return new DepositPersistenceAdapter(repository, mapper);
  }
}
