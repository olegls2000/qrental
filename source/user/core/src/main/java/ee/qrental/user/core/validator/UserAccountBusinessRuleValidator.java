package ee.qrental.user.core.validator;

import static java.lang.String.format;

import ee.qrent.common.in.validation.QValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrental.driver.domain.UserAccount;
import ee.qrental.user.api.out.UserAccountLoadPort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserAccountBusinessRuleValidator implements QValidator<UserAccount> {

  private final UserAccountLoadPort loadPort;

  @Override
  public ViolationsCollector validateAdd(final UserAccount domain) {
    final var violationsCollector = new ViolationsCollector();
    checkEmailUniquenessForAdd(domain, violationsCollector);
    checkUsernameUniquenessForAdd(domain, violationsCollector);

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateUpdate(final UserAccount domain) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }

  @Override
  public ViolationsCollector validateDelete(final Long id) {
    final var violationsCollector = new ViolationsCollector();

    return violationsCollector;
  }
  
  private void checkEmailUniquenessForAdd(
          final UserAccount domain, final ViolationsCollector violationCollector) {
    final var email = domain.getEmail();
    final var domainFromDb = loadPort.loadByEmail(email);
    if (domainFromDb == null) {
      return;
    }

    violationCollector.collect(format("User with email: %d already exists", email));
  }

  private void checkUsernameUniquenessForAdd(
          final UserAccount domain, final ViolationsCollector violationCollector) {
    final var username = domain.getUsername();
    final var domainFromDb = loadPort.loadByUsername(username);
    if (domainFromDb == null) {
      return;
    }

    violationCollector.collect(format("User Account with username: %s already exists", username));
  }
}
