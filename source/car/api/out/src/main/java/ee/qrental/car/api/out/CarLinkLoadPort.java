package ee.qrental.car.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.car.domain.CarLink;

import java.util.List;

public interface CarLinkLoadPort extends LoadPort<CarLink> {
  CarLink loadActiveByDriverId(final Long driverId);

  List<CarLink> loadActiveByCarId(final Long carId);
}
