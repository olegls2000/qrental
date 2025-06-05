package ee.qrent.billing.bolt.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.InputStream;
import java.time.LocalDate;

@SuperBuilder
@Getter
@Setter
public class BoltStatistics {
  private Long id;
  private String fileName;
  private LocalDate createdOn;
  private String region;
  private InputStream inputStream;
  private Integer year;
  private Integer month;
}
