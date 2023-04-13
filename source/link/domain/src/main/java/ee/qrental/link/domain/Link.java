package ee.qrental.link.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Link {
  private Long id;
  private Long carId;
  private Long driverId;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;
}
