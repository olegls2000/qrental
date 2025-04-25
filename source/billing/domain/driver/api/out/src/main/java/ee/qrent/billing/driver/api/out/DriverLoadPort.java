package ee.qrent.billing.driver.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.driver.domain.Driver;

import java.util.List;

public interface DriverLoadPort extends LoadPort<Driver> {
  List<Driver> loadByMatchCountAndQWeekId(
      final Integer matchCount, final Long latestCalculatedQWeekId);

  Driver loadByTaxNumber(final Long taxNumber);
}
