package ee.qrental.bonus.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WeekObligationAddRequest extends AbstractAddRequest {
  private Long qWeekId;
}
