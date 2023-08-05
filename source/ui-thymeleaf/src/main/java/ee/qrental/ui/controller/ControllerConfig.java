package ee.qrental.ui.controller;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.transaction.api.in.query.balance.GetBalanceQuery;
import ee.qrental.ui.controller.transaction.assembler.DriverBalanceAssembler;
import java.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "ee.qrental.ui.controller")
public class ControllerConfig implements WebMvcConfigurer {
  public void addFormatters(FormatterRegistry registry) {
    registry.addFormatter(getQDateFormatter());
  }
  
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
