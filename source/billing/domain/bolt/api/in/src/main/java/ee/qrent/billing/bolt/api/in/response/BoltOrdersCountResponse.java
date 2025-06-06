package ee.qrent.billing.bolt.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BoltOrdersCountResponse {
  private Long id;
  private String driverName;
  private Integer monthOrdersCount;
  private String month;
  private Integer insuranceDiscount;
}
