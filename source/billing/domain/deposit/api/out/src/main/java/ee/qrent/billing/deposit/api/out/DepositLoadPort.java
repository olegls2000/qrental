package ee.qrent.billing.deposit.api.out;

import ee.qrent.billing.deposit.domain.Deposit;
import ee.qrent.common.out.port.LoadPort;

import java.util.List;

public interface DepositLoadPort extends LoadPort<Deposit> {
  List<Deposit> loadAllByDriverId(final Long driverId);
}
