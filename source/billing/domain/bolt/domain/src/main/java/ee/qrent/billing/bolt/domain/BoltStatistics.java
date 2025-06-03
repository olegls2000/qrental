package ee.qrent.billing.bolt.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class BoltStatistics {
  private Long id;
  private String fileName;
  private LocalDate createdOn;
  private String region;
  private byte[] data;
  private String comment;
}
