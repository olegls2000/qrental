package ee.qrental.bonus.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BonusAddRequest extends AbstractAddRequest {
  private Long driverId;
  private Long qWeekId;
}
