package ee.qrent.billing.contract.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class AbsenceResponse {
  private Long id;
  private Long driverId;
  private String driverFirstName;
  private String driverLastName;
  private Long driverIsikukood;
  private String reason;
  private Boolean withCar;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;
}
