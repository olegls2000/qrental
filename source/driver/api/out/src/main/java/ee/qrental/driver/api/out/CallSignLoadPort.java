package ee.qrental.driver.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.driver.domain.CallSign;
import java.util.List;

public interface CallSignLoadPort extends LoadPort<CallSign> {
  CallSign loadByCallSign(final Integer callSign);

  List<CallSign> loadAvailable();
}
