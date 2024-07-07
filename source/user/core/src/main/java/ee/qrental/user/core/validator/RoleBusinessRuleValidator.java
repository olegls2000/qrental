package ee.qrental.user.core.validator;


import static java.lang.String.format;

import ee.qrent.common.in.validation.QValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.driver.domain.Role;
import ee.qrental.user.api.out.RoleLoadPort;
import ee.qrental.user.api.out.UserAccountLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleBusinessRuleValidator implements QValidator<Role> {

  private final RoleLoadPort loadPort;
  private final UserAccountLoadPort userAccountLoadPort;

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
    checkReferences(id, violationsCollector);

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

  private void checkReferences(
          final Long id, final ViolationsCollector violationCollector) {

    final var userAccounts = userAccountLoadPort.loadByRoleId(id);
    if (userAccounts.isEmpty()) {
      return;
    }
    violationCollector.collect(
            format(
                    "Role with id: %d can not be deleted, because it assigned to the %d User Accounts",
                    id, userAccounts.size()));
  }
}
