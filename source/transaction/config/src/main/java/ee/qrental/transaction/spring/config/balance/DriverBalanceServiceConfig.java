package ee.qrental.transaction.spring.config.balance;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.GetDriverBalanceQuery;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.core.service.balance.BalancesQueryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverBalanceServiceConfig {

  @Bean
  GetDriverBalanceQuery getGetBalanceQueryService(
      final TransactionLoadPort transactionLoadPort, final GetDriverQuery driverQuery) {
    return new BalancesQueryService(transactionLoadPort, driverQuery);
  }
}
