package ee.qrental.car.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.car.domain.CarLink;

import java.time.LocalDate;
import java.util.List;

public interface CarLinkLoadPort extends LoadPort<CarLink> {
  CarLink loadActiveByDriverId(final Long driverId);

  List<CarLink> loadActive();

  List<CarLink> loadActiveByDate(final LocalDate date);

  List<CarLink> loadClosedByDate(final LocalDate date);

  List<CarLink> loadActiveByCarId(final Long carId);
}
