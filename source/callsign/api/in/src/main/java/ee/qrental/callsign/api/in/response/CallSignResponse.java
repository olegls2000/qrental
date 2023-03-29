package ee.qrental.callsign.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class CallSignResponse {
  private Long id;
  private Integer callSign;
  private String comment;
}
