package ee.qrent.billing.deposit.config.spring;

import ee.qrent.billing.deposit.api.in.query.GetDepositQuery;
import ee.qrent.billing.deposit.api.out.DepositAddPort;
import ee.qrent.billing.deposit.api.out.DepositDeletePort;
import ee.qrent.billing.deposit.api.out.DepositLoadPort;
import ee.qrent.billing.deposit.api.out.DepositUpdatePort;
import ee.qrent.billing.deposit.core.mapper.DepositAddRequestMapper;
import ee.qrent.billing.deposit.core.mapper.DepositResponseMapper;
import ee.qrent.billing.deposit.core.mapper.DepositUpdateRequestMapper;
import ee.qrent.billing.deposit.core.service.DepositQueryService;
import ee.qrent.billing.deposit.core.service.DepositUseCaseService;
import ee.qrent.billing.deposit.core.validator.DepositRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepositServiceConfig {

  @Bean
  GetDepositQuery getDepositQueryService(
      final DepositLoadPort loadPort,
      final DepositResponseMapper mapper,
      final DepositUpdateRequestMapper updateRequestMapper) {

    return new DepositQueryService(loadPort, mapper, updateRequestMapper);
  }

  @Bean
  public DepositUseCaseService getDepositUseCaseService(
      final DepositAddPort addPort,
      final DepositUpdatePort updatePort,
      final DepositDeletePort deletePort,
      final DepositLoadPort loadPort,
      final DepositAddRequestMapper addRequestMapper,
      final DepositUpdateRequestMapper updateRequestMapper,
      final DepositRequestValidator requestValidator) {

    return new DepositUseCaseService(
        addPort,
        updatePort,
        deletePort,
        loadPort,
        addRequestMapper,
        updateRequestMapper,
        requestValidator);
  }
}
