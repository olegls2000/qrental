package ee.qrental.driver.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.driver.domain.Driver;

public interface DriverLoadPort extends LoadPort<Driver> {
  Driver loadByIsukuKood(final Long isukuKood);
}
