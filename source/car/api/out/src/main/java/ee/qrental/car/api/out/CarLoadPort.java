package ee.qrental.car.api.out;


import ee.qrent.common.out.port.LoadPort;
import ee.qrental.car.domain.Car;

import java.time.LocalDate;
import java.util.List;

public interface CarLoadPort extends LoadPort<Car> {
  List<Car> loadNotAvailableByDate(final LocalDate date);

  List<Car> loadByActive(final boolean active);

}
