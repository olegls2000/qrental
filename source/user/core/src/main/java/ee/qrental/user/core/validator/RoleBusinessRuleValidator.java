package ee.qrental.user.core.validator;


import static java.lang.String.format;

import ee.qrental.common.core.in.validation.QValidator;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import ee.qrental.driver.domain.Role;
import ee.qrental.user.api.out.RoleLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleBusinessRuleValidator implements QValidator<Role> {

  private final RoleLoadPort loadPort;

  @Override
  public ViolationsCollector validateAdd(final Role domain) {
    final var violationsCollector = new ViolationsCollector();
    checkUniquenessForAdd(domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(final Role domain) {
    final var violationsCollector = new ViolationsCollector();
    checkUniquenessForAdd(domain, violationsCollector);
    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }

  private void checkUniquenessForAdd(
          final Role domain, final ViolationsCollector violationCollector) {
    final var name = domain.getName();
    final var domainFromDb = loadPort.loadByName(name);
    if (domainFromDb == null) {
      return;
    }

    violationCollector.collect(format("Role with name: %s already exists", name));
  }
}
