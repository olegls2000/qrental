package ee.qrental.contract.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.contract.domain.Authorization;

public interface AuthorizationLoadPort extends LoadPort<Authorization> {
    Authorization loadByDriverId(final Long driverId);
}
