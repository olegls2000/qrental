package ee.qrental.driver.api.in.request;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FirmLinkUpdateRequest extends AbstractUpdateRequest {

  private Long driverId;
  private Long firmId;
  private LocalDate dateStart;
  private LocalDate dateEnd;
  private String comment;
}
