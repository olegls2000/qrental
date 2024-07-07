package ee.qrental.driver.api.in.request;

import ee.qrent.common.in.request.AbstractRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CallSignLinkCloseRequest extends AbstractRequest {
  private Long id;
  private Long driverId;
}
