package ee.qrent.billing.task.core.mapper;

import static java.lang.String.format;

import ee.qrent.billing.task.api.in.response.TaskRunResultResponse;
import ee.qrent.billing.task.domain.TaskRunResult;
import ee.qrent.common.in.mapper.ResponseMapper;

public class TaskRunResultResponseMapper
    implements ResponseMapper<TaskRunResultResponse, TaskRunResult> {

  @Override
  public TaskRunResultResponse toResponse(final TaskRunResult domain) {

    return TaskRunResultResponse.builder()
        .taskName(domain.getTaskName())
        .status(domain.getStatus())
        .durationInMillis(domain.getDurationInMillis())
        .startedAt(domain.getStartedAt())
        .build();
  }

  @Override
  public String toObjectInfo(final TaskRunResult domain) {
    return format(
        "%s %s, started at: %s",
        domain.getTaskName(), domain.getStatus(), domain.getStartedAt().toString());
  }
}
