package ee.qrent.billing.contract.api.in.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthorizationResponse {
  private Long id;
  private Long driverId;
  private Long driverIsikukood;
  private String driverFirstName;
  private String driverLastName;
  private String driverEmail;
  private LocalDate created;

}

