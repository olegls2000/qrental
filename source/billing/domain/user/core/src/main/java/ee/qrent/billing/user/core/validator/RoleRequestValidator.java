package ee.qrent.billing.user.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.user.api.in.request.RoleAddRequest;
import ee.qrent.billing.user.api.in.request.RoleDeleteRequest;
import ee.qrent.billing.user.api.in.request.RoleUpdateRequest;
import ee.qrent.billing.user.api.out.RoleLoadPort;
import ee.qrent.billing.user.api.out.UserAccountLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RoleRequestValidator
    implements AddRequestValidator<RoleAddRequest>,
        UpdateRequestValidator<RoleUpdateRequest>,
        DeleteRequestValidator<RoleDeleteRequest> {

  private final RoleLoadPort loadPort;
  private final UserAccountLoadPort userAccountLoadPort;

  @Override
  public ViolationsCollector validate(final RoleAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkUniqueness(request.getName(), violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validate(final RoleUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkUniqueness(request.getName(), violationsCollector);
    return violationsCollector;
  }

  @Override
  public ViolationsCollector validate(final RoleDeleteRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkReferences(request.getId(), violationsCollector);

    return violationsCollector;
  }

  private void checkUniqueness(final String name, final ViolationsCollector violationCollector) {
    final var domainFromDb = loadPort.loadByName(name);
    if (domainFromDb == null) {
      return;
    }

    violationCollector.collect(format("Role with name: %s already exists", name));
  }

  private void checkReferences(final Long id, final ViolationsCollector violationCollector) {

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
