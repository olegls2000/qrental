package ee.qrental.driver.api.in.request;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class CallSignUpdateRequest extends AbstractUpdateRequest {
  private Integer callSign;
  private String comment;
}
