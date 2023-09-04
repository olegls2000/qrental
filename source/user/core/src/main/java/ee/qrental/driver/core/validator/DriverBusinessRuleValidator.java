package ee.qrental.driver.core.validator;

import ee.qrental.common.core.in.validation.QValidator;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.domain.Driver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverBusinessRuleValidator implements QValidator<Driver> {

  private final DriverLoadPort loadPort;

  @Override
  public ViolationsCollector validateAdd(Driver domain) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(Driver domain) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }
}
