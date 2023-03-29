package ee.qrental.callsign.api.out;

import ee.qrental.callsign.domain.CallSign;
import ee.qrental.common.core.out.port.LoadPort;

public interface CallSignLoadPort extends LoadPort<CallSign> {
  CallSign loadByCallSign(final Integer callSign);
}
