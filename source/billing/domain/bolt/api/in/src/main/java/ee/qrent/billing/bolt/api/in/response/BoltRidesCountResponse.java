package ee.qrent.billing.bolt.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BoltRidesCountResponse {
  private Long id;
  private String driverName;
  private Integer monthRidesCount;
  private String month;
  private Integer year;
  private Integer insuranceRate;
}
