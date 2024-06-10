package ee.qrental.driver.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.driver.domain.Friendship;

public interface FriendshipLoadPort extends LoadPort<Friendship> {
  Friendship loadByFriendId(final Long friendId);
}
