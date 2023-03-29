package ee.qrental.callsign.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallSignAddRequest extends AbstractAddRequest {
  private Integer callSign;
  private String comment;
}
