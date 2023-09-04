package ee.qrental.driver.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class FirmLink {
  private Long id;
  private Long firmId;
  private Long driverId;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;

  public boolean isActive() {
    if (dateEnd == null) {
      return true;
    }
    if (dateEnd.isAfter(LocalDate.now())) {
      return true;
    }
    return false;
  }
}
