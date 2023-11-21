package ee.qrental.contract.api.in.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthorizationBoltResponse {
  private Long id;
  private Long driverId;
  private Long driverIsikukood;
  private String driverFirstName;
  private String driverLastName;
  private String driverEmail;
  private LocalDate created;

}

