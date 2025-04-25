package ee.qrent.billing.driver.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FirmLinkAddRequest extends AbstractAddRequest {
  private Long driverId;
  private Long firmId;
  private LocalDate dateStart = LocalDate.now();
  private LocalDate dateEnd;
  private String comment;
}
