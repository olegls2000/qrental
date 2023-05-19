package ee.qrental.driver.api.in.response;

import java.time.LocalDate;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CallSignLinkResponse {
  private Long id;
  private Long driverId;
  private String driverInfo;
  private Long callSignId;
  private Integer callSign;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;
}
