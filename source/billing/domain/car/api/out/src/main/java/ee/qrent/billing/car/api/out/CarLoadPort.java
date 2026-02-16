package ee.qrent.billing.car.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.car.domain.Car;

import java.time.LocalDate;
import java.util.List;

public interface CarLoadPort extends LoadPort<Car> {
  List<Car> loadNotAvailableByDate(final LocalDate date);

  List<Car> loadByActive(final boolean active);

  Car loadByRegNumber(final String regNumber);

  Car loadByVin(final String vin);

  Car loadByBoltIdentifier(final String boltIdentifier);

  Long loadCountByActive(final boolean active);

  Long loadCountByStatus(final String status);

  Long loadCountBrandingControl();

  Long loadCountAvailableByDate(final LocalDate date);

  Long loadCountAll();
}
