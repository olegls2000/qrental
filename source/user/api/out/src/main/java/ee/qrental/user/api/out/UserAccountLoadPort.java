package ee.qrental.user.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.driver.domain.UserAccount;

import java.util.List;

public interface UserAccountLoadPort extends LoadPort<UserAccount> {
  UserAccount loadByEmail(final String email);

  UserAccount loadByUsername(final String username);

  List<UserAccount> loadByRoleId(final Long roleId);

  List<UserAccount> loadByRoleName(final String roleName);
}
