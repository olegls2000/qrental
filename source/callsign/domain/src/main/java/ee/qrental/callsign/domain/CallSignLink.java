package ee.qrental.callsign.domain;

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
}
