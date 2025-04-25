package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.persistence.repository.AbsenceRepository;
import ee.qrent.billing.contract.persistence.repository.impl.AbsenceRepositoryImpl;
import ee.qrent.billing.contract.persistence.repository.spring.AbsenceSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbsenceRepositoryConfig {

  @Bean
  AbsenceRepository getAbsenceRepository(final AbsenceSpringDataRepository springDataRepository) {
    return new AbsenceRepositoryImpl(springDataRepository);
  }
}
