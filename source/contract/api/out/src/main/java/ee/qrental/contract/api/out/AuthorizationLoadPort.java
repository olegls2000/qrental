package ee.qrental.contract.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.contract.domain.Authorization;

public interface AuthorizationLoadPort extends LoadPort<Authorization> {
    Authorization loadByDriverId(final Long driverId);
}
