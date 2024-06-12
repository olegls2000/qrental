package ee.qrental.driver.api.in.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
