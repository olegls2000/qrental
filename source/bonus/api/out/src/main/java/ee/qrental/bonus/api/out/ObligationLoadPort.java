package ee.qrental.bonus.api.out;

import ee.qrental.bonus.domain.Obligation;
import ee.qrental.common.core.out.port.LoadPort;

import java.util.List;

public interface ObligationLoadPort extends LoadPort<Obligation> {
  List<Obligation> loadAllByIds(final List<Long> ids);

  Obligation loadByDriverIdAndByQWeekId(final Long driverId, final Long qWeekId);
}
