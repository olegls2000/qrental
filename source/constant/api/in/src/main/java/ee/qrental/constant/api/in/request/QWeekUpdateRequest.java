package ee.qrental.constant.api.in.request;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class QWeekUpdateRequest extends AbstractUpdateRequest {
  private String description;
}
