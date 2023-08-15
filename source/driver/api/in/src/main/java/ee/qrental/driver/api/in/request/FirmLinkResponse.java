package ee.qrental.driver.api.in.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class FirmLinkResponse {
  private Long id;
  private Long driverId;
  private String driverInfo;
  private Long firmId;
  private String firmInfo;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;
}
