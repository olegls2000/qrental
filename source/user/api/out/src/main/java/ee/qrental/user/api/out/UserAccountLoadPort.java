package ee.qrental.user.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.driver.domain.UserAccount;

public interface UserAccountLoadPort extends LoadPort<UserAccount> {
    UserAccount loadByEmail(final String email);
    UserAccount loadByUsername(final String username);
}
