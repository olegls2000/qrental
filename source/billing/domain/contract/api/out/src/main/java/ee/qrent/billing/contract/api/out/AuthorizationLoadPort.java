package ee.qrent.billing.contract.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.contract.domain.Authorization;

public interface AuthorizationLoadPort extends LoadPort<Authorization> {
    Authorization loadByDriverId(final Long driverId);

    Authorization loadLatestByDriverId(final Long driverId);
}
