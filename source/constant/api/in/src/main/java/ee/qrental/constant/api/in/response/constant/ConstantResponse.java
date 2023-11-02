package ee.qrental.constant.api.in.response.constant;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;

@SuperBuilder
@Getter
public class ConstantResponse {
  private Long id;
  private String constant;
  private BigDecimal value;
  private String description;
  private Boolean negative;
}
