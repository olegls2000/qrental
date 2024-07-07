package ee.qrental.constant.api.in.request;


import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ConstantUpdateRequest extends AbstractUpdateRequest {
  private String constant;
  private BigDecimal value;
  private String description;
  private Boolean negative;
}
