package ee.qrental.ui.controller;

import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.ui.controller.transaction.assembler.DriverBalanceAssembler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.time.LocalDate;

@Configuration
@ComponentScan(basePackages = "ee.qrental.ui.controller")
public class ControllerConfig {

  @Bean
  Formatter<LocalDate> getQDateFormatter() {
    return new QDateFormatter();
  }

  @Bean
  DriverBalanceAssembler getDriverBalanceAssembler(
      final GetBalanceQuery balanceQuery, final GetDriverQuery driverQuery) {
    return new DriverBalanceAssembler(balanceQuery, driverQuery);
  }
}
