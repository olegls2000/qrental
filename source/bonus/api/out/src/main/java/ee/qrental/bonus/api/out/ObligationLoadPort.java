package ee.qrental.bonus.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.bonus.domain.Obligation;

import java.util.List;

public interface ObligationLoadPort extends LoadPort<Obligation> {
  List<Obligation> loadAllByIds(final List<Long> ids);

  List<Obligation> loadAllByCalculationId(final Long id);

  Obligation loadByDriverIdAndByQWeekId(final Long driverId, final Long qWeekId);
}
