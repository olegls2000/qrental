package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.persistence.adapter.AbsenceLoadAdapter;
import ee.qrent.billing.contract.persistence.adapter.AbsencePersistenceAdapter;
import ee.qrent.billing.contract.persistence.mapper.AbsenceAdapterMapper;
import ee.qrent.billing.contract.persistence.repository.AbsenceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbsenceAdapterConfig {
  @Bean
  AbsenceAdapterMapper getAbsenceAdapterMapper() {
    return new AbsenceAdapterMapper();
  }

  @Bean
  AbsenceLoadAdapter getAbsenceLoadAdapter(
      final AbsenceRepository repository, final AbsenceAdapterMapper mapper) {
    return new AbsenceLoadAdapter(repository, mapper);
  }

  @Bean
  AbsencePersistenceAdapter getAbsencePersistenceAdapter(
      final AbsenceRepository repository, final AbsenceAdapterMapper mapper) {
    return new AbsencePersistenceAdapter(repository, mapper);
  }
}
