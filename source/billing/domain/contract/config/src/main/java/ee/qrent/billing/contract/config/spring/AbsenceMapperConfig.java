package ee.qrent.billing.contract.config.spring;

import ee.qrent.billing.contract.api.out.AbsenceLoadPort;
import ee.qrent.billing.contract.core.mapper.AbsenceAddRequestMapper;
import ee.qrent.billing.contract.core.mapper.AbsenceResponseMapper;
import ee.qrent.billing.contract.core.mapper.AbsenceUpdateRequestMapper;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbsenceMapperConfig {
  @Bean
  AbsenceAddRequestMapper getAbsenceAddRequestMapper() {

    return new AbsenceAddRequestMapper();
  }

  @Bean
  AbsenceUpdateRequestMapper getAbsenceUpdateRequestMapper(final AbsenceLoadPort loadPort) {

    return new AbsenceUpdateRequestMapper(loadPort);
  }

  @Bean
  AbsenceResponseMapper getAbsenceResponseMapper(final GetDriverQuery driverQuery) {

    return new AbsenceResponseMapper(driverQuery);
  }
}
