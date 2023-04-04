package ee.qrental.ui.controller;

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
}
