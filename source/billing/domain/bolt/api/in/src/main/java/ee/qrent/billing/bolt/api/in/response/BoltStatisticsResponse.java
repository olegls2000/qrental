package ee.qrent.billing.bolt.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class BoltStatisticsResponse {
  private Long id;
  private String fileName;
  private String region;
  private LocalDate actionDate;
  private String comment;
}
