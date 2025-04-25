package ee.qrent.billing.user.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.user.domain.Role;

public interface RoleLoadPort extends LoadPort<Role> {
    Role loadByName(final String name);
}
