package ee.qrent.billing.user.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.user.api.in.request.UserAccountAddRequest;
import ee.qrent.billing.user.api.in.request.UserAccountDeleteRequest;
import ee.qrent.billing.user.api.in.request.UserAccountUpdateRequest;
import ee.qrent.billing.user.api.out.UserAccountLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAccountRequestValidator
    implements AddRequestValidator<UserAccountAddRequest>,
        UpdateRequestValidator<UserAccountUpdateRequest>,
        DeleteRequestValidator<UserAccountDeleteRequest> {

  private final UserAccountLoadPort loadPort;

  @Override
  public ViolationsCollector validate(final UserAccountAddRequest request) {
    final var violationsCollector = new ViolationsCollector();
    checkEmailUniquenessForAdd(request, violationsCollector);
    checkUsernameUniquenessForAdd(request, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validate(final UserAccountUpdateRequest request) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validate(final UserAccountDeleteRequest request) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }

  private void checkEmailUniquenessForAdd(
      final UserAccountAddRequest domain, final ViolationsCollector violationCollector) {
    final var email = domain.getEmail();
    final var domainFromDb = loadPort.loadByEmail(email);
    if (domainFromDb == null) {
      return;
    }

    violationCollector.collect(format("User with email: %d already exists", email));
  }

  private void checkUsernameUniquenessForAdd(
      final UserAccountAddRequest domain, final ViolationsCollector violationCollector) {
    final var username = domain.getUsername();
    final var domainFromDb = loadPort.loadByUsername(username);
    if (domainFromDb == null) {
      return;
    }

    violationCollector.collect(format("User Account with username: %s already exists", username));
  }
}
