package ee.qrental.user.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.driver.domain.Role;

public interface RoleLoadPort extends LoadPort<Role> {
    Role loadByName(final String name);
}
