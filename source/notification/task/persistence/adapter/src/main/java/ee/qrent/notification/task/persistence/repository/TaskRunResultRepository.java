package ee.qrent.notification.task.persistence.repository;

import ee.qrent.notification.task.persistence.entity.jakarta.TaskRunResultJakartaEntity;
import java.util.List;

public interface TaskRunResultRepository {
  List<TaskRunResultJakartaEntity> findAll();

  List<TaskRunResultJakartaEntity> findAllByTaskName(final String taskName);

  TaskRunResultJakartaEntity getReferenceById(final Long id);

  TaskRunResultJakartaEntity save(final TaskRunResultJakartaEntity entity);

  List<String> findAllTaskNames();
}
