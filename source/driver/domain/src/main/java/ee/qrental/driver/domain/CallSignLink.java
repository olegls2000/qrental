package ee.qrental.driver.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class CallSignLink {
  private Long id;
  private CallSign callSign;
  private Long driverId;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;

  public boolean isActive() {
    if (dateEnd == null) {
      return true;
    }

    return dateEnd.isAfter(LocalDate.now());
  }
}
