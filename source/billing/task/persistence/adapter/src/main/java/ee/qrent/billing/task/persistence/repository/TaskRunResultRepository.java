package ee.qrent.billing.task.persistence.repository;

import ee.qrent.billing.task.persistence.entity.jakarta.TaskRunResultJakartaEntity;
import java.util.List;

public interface TaskRunResultRepository {
  List<TaskRunResultJakartaEntity> findAllByTaskTame(final String taskName);

  TaskRunResultJakartaEntity save(final TaskRunResultJakartaEntity entity);
}
