package ee.qrental.ui.controller;

import ee.qrental.bonus.api.in.query.GetObligationQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import ee.qrental.ui.controller.transaction.assembler.DriverBalanceAssembler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ee.qrental.ui.controller")
public class ControllerConfig {

  @Bean
  QDateFormatter getQDateFormatter() {
    return new QDateFormatter();
  }

  @Bean
  DriverBalanceAssembler getDriverBalanceAssembler(
      final GetBalanceQuery balanceQuery,
      final GetDriverQuery driverQuery,
      final GetObligationQuery obligationQuery) {
    return new DriverBalanceAssembler(balanceQuery, driverQuery, obligationQuery);
  }
}
