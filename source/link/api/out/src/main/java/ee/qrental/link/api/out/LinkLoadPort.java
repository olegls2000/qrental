package ee.qrental.link.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.link.domain.Link;

import java.util.List;

public interface LinkLoadPort extends LoadPort<Link> {
  Link loadActiveByDriverId(final Long driverId);
  List<Link> loadActiveByCarId(final Long carId);
}
