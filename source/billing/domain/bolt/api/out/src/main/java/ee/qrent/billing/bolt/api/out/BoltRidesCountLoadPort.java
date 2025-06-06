package ee.qrent.billing.bolt.api.out;

import ee.qrent.billing.bolt.domain.BoltOrdersCount;

import java.util.List;

public interface BoltRidesCountLoadPort {

  BoltOrdersCount loadByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  List<BoltOrdersCount> loadAllByYearAndMonth(final Integer year, final Integer month);
}
