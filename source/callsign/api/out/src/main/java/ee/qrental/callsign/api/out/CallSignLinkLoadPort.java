package ee.qrental.callsign.api.out;

import ee.qrental.callsign.domain.CallSignLink;
import ee.qrental.common.core.out.port.LoadPort;
import java.util.List;

public interface CallSignLinkLoadPort extends LoadPort<CallSignLink> {

  List<CallSignLink> loadActiveCallSignLinks();

  CallSignLink loadByDriverId(final Long driverId);
}
