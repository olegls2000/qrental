package ee.qrent.billing.driver.api.in.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class FriendshipResponse {
  private Long id;
  private Long driverId;
  private Long friendId;
  private LocalDate createdDate;
}
