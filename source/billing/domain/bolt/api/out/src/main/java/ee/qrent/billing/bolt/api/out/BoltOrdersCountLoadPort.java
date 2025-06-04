package ee.qrent.billing.bolt.api.out;

import ee.qrent.billing.bolt.domain.BoltOrdersCount;

public interface BoltOrdersCountLoadPort {

  BoltOrdersCount loadByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);
}
