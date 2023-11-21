package ee.qrental.contract.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.contract.domain.AuthorizationBolt;
import ee.qrental.contract.domain.Contract;

public interface AuthorizationBoltLoadPort extends LoadPort<AuthorizationBolt> {
    AuthorizationBolt loadByDriverId(final Long driverId);
}
