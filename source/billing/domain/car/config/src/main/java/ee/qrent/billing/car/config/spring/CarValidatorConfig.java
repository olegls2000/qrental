package ee.qrent.billing.car.config.spring;

import ee.qrent.billing.car.api.out.CarLoadPort;
import ee.qrent.billing.car.core.validator.CarAddRequestValidator;
import ee.qrent.billing.car.core.validator.CarUpdateRequestValidator;

import ee.qrent.common.in.validation.AttributeChecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarValidatorConfig {

  @Bean
  CarAddRequestValidator getCarAddRequestValidator(
      final CarLoadPort loadPort, final AttributeChecker attributeChecker) {

    return new CarAddRequestValidator(attributeChecker, loadPort);
  }

  @Bean
  CarUpdateRequestValidator getCarUpdateRequestValidator(
      final CarLoadPort loadPort, final AttributeChecker attributeChecker) {

    return new CarUpdateRequestValidator(attributeChecker, loadPort);
  }
}
