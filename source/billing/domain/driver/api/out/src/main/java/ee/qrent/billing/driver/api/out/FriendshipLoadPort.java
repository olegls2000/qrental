package ee.qrent.billing.driver.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.driver.domain.Friendship;

import java.util.List;

public interface FriendshipLoadPort extends LoadPort<Friendship> {
  List<Friendship> loadByDriverId(final Long driverId);
}
