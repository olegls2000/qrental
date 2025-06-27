package ee.qrent.billing.task.api.in.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TaskRunResultResponse {
  private String taskName;
  private String status;
  private LocalDateTime startedAt;
  private Long durationInMillis;
}
