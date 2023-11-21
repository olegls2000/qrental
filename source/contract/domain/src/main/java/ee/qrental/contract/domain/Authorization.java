package ee.qrental.contract.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Authorization {
  private Long id;
  private Long driverId;
  private Long driverIsikukood;
  private String driverFirstName;
  private String driverLastName;
  private String driverEmail;
  private LocalDate created;

}
