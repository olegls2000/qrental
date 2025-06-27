package ee.qrent.billing.task.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@ToString
public class TaskRunResult {
  private Long id;
  private String taskName;
  private LocalDateTime startedAt;
  private Long durationInMillis;
  private String status;
}
