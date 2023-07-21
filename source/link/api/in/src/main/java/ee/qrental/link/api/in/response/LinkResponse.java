package ee.qrental.link.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class LinkResponse {
  private Long id;
  private String carInfo;
  private String registrationNumber;
  private Long carId;
  private String driverInfo;
  private Long driverId;
  private Integer callSign;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;
}
