package ee.qrent.billing.driver.config.spring;

import ee.qrent.billing.driver.api.out.DriverLoadPort;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.request.DriverAddRequest;
import ee.qrent.billing.driver.api.in.request.DriverDeleteRequest;
import ee.qrent.billing.driver.api.in.request.DriverUpdateRequest;
import ee.qrent.billing.driver.core.validator.DriverAddRequestValidator;
import ee.qrent.billing.driver.core.validator.DriverDeleteRequestValidator;
import ee.qrent.billing.driver.core.validator.DriverUpdateRequestValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DriverValidatorConfig {

  @Bean
  AddRequestValidator<DriverAddRequest> getDriverAddRequestValidator(
      final DriverLoadPort loadPort,
      final QDateTime qDateTime,
      final AttributeChecker attributeChecker) {

    return new DriverAddRequestValidator(attributeChecker, loadPort, qDateTime);
  }

  @Bean
  UpdateRequestValidator<DriverUpdateRequest> getDriverUpdateRequestValidator(
      final DriverLoadPort loadPort,
      final GetQWeekQuery qWeekQuery,
      final QDateTime qDateTime,
      final AttributeChecker attributeChecker) {

    return new DriverUpdateRequestValidator(attributeChecker, loadPort, qWeekQuery, qDateTime);
  }

  @Bean
  DeleteRequestValidator<DriverDeleteRequest> getDriverDeleteRequestValidator(
      final DriverLoadPort loadPort, final GetQWeekQuery qWeekQuery, final QDateTime qDateTime) {

    return new DriverDeleteRequestValidator(loadPort, qWeekQuery, qDateTime);
  }
}
