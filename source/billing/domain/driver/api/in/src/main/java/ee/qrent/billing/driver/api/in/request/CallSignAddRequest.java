package ee.qrent.billing.driver.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallSignAddRequest extends AbstractAddRequest {
  private Integer callSign;
  private String comment;
}
