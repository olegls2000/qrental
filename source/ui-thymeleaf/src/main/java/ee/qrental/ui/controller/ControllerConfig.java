package ee.qrental.ui.controller;

import ee.qrental.balance.api.in.query.GetBalanceQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
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
      final GetBalanceQuery balanceQuery, final GetDriverQuery driverQuery) {
    return new DriverBalanceAssembler(balanceQuery, driverQuery);
  }
}
