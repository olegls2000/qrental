package ee.qrental.driver.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CallSignLinkAddRequest extends AbstractAddRequest {

  private Long driverId;
  private Long callSignId;
  private LocalDate dateStart = LocalDate.now();
  private LocalDate dateEnd;
  private String comment;
}
