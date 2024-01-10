package ee.qrental.bonus.api.in.request;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class WeekObligationUpdateRequest extends AbstractUpdateRequest {

  private String comment;
}
