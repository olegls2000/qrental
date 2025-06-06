package ee.qrent.billing.bolt.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class BoltOrdersCount {
  private Long id;
  private String boltId;
  private Long driverId;
  private Long qWeekId;
  private Integer month;
  private Integer year;
  private Integer monthOrdersCount;
}
