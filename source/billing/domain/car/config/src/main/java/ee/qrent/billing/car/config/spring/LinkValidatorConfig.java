package ee.qrent.billing.car.config.spring;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.car.api.in.request.CarLinkAddRequest;
import ee.qrent.billing.car.api.in.request.CarLinkDeleteRequest;
import ee.qrent.billing.car.api.in.request.CarLinkUpdateRequest;
import ee.qrent.billing.car.api.out.CarLinkLoadPort;
import ee.qrent.billing.car.core.validator.CarLinkDeleteRequestValidator;
import ee.qrent.billing.car.core.validator.CarLinkUpdateRequestValidator;
import ee.qrent.billing.car.core.validator.CarLinkAddRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkValidatorConfig {

  @Bean
  AddRequestValidator<CarLinkAddRequest> getCarLinkAddRequestValidator(
      final CarLinkLoadPort loadPort) {
    return new CarLinkAddRequestValidator(loadPort);
  }

  @Bean
  UpdateRequestValidator<CarLinkUpdateRequest> getCarLinkUpdateRequestValidator(
      final CarLinkLoadPort loadPort) {

    return new CarLinkUpdateRequestValidator(loadPort);
  }

  @Bean
  DeleteRequestValidator<CarLinkDeleteRequest> getCarLinkDeleteRequestValidator(
      final CarLinkLoadPort loadPort) {
    return new CarLinkDeleteRequestValidator(loadPort);
  }
}
