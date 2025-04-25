package ee.qrent.billing.car.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.car.domain.CarLink;

import java.time.LocalDate;
import java.util.List;

public interface CarLinkLoadPort extends LoadPort<CarLink> {
  CarLink loadActiveByDriverId(final Long driverId);

  CarLink loadFirstByDriverId(final Long driverId);

  List<CarLink> loadActive();

  List<CarLink> loadActiveByDate(final LocalDate date);

  Long loadCountActiveByDate(final LocalDate date);

  List<CarLink> loadClosedByDate(final LocalDate date);

  Long loadCountClosedByDate(final LocalDate date);

  List<CarLink> loadActiveByCarId(final Long carId);
}
