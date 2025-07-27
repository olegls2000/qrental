package ee.qrent.notification.task.persistence.mapper;

import ee.qrent.notification.task.domain.TaskRunResult;
import ee.qrent.notification.task.persistence.entity.jakarta.TaskRunResultJakartaEntity;

public class TaskRunResultAdapterMapper {

  public TaskRunResult mapToDomain(final TaskRunResultJakartaEntity entity) {

    return TaskRunResult.builder()
        .id(entity.getId())
        .taskName(entity.getTaskName())
        .status(entity.getStatus())
        .startedAt(entity.getStartedAt())
        .durationInMillis(entity.getDurationInMillis())
        .build();
  }

  public TaskRunResultJakartaEntity mapToEntity(final TaskRunResult domain) {

    return TaskRunResultJakartaEntity.builder()
        .id(domain.getId())
        .taskName(domain.getTaskName())
        .status(domain.getStatus())
        .startedAt(domain.getStartedAt())
        .durationInMillis(domain.getDurationInMillis())
        .build();
  }
}
