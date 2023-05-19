package ee.qrental.driver.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.driver.domain.CallSign;

public interface CallSignLoadPort extends LoadPort<CallSign> {
  CallSign loadByCallSign(final Integer callSign);
}
