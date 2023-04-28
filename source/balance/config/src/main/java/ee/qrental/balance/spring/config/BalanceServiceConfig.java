package ee.qrental.balance.spring.config;

import ee.qrental.balance.api.in.query.GetBalanceQuery;
import ee.qrental.balance.api.out.BalanceAddPort;
import ee.qrental.balance.api.out.BalanceCalculationAddPort;
import ee.qrental.balance.api.out.BalanceCalculationLoadPort;
import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.balance.core.mapper.*;
import ee.qrental.balance.core.service.*;
import ee.qrental.balance.core.validator.BalanceCalculationBusinessRuleValidator;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BalanceServiceConfig {

  @Bean
  GetBalanceQuery getBalanceQueryService(
      final BalanceLoadPort loadPort, final BalanceResponseMapper mapper) {
    return new BalanceQueryService(loadPort, mapper);
  }

  @Bean
  BalanceCalculationQueryService getBalanceCalculationQueryService(
      final BalanceCalculationLoadPort loadPort,
      final BalanceCalculationResponseMapper responseMapper) {
    return new BalanceCalculationQueryService(loadPort, responseMapper);
  }

  @Bean
  BalanceCalculationService getBalanceCalculationService(
      final BalanceCalculationPeriodService balanceCalculationPeriodService,
      final BalanceCalculationAddRequestMapper addRequestMapper,
      final BalanceCalculationBusinessRuleValidator businessRuleValidator,
      final BalanceCalculationAddPort addPort,
      final BalanceLoadPort loadPort,
      final BalanceAddPort balanceAddPort,
      final GetTransactionQuery transactionQuery) {
    return new BalanceCalculationService(
        balanceCalculationPeriodService,
        addRequestMapper,
        businessRuleValidator,
        addPort,
        loadPort,
        balanceAddPort,
        transactionQuery);
  }

  @Bean
  BalanceCalculationPeriodService getBalanceCalculationPeriodService(
      final BalanceCalculationLoadPort loadPort) {
    return new BalanceCalculationPeriodService(loadPort);
  }
}
