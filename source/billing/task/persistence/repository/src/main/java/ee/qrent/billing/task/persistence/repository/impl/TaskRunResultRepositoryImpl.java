package ee.qrent.billing.task.persistence.repository.impl;

import ee.qrent.billing.task.persistence.entity.jakarta.TaskRunResultJakartaEntity;
import ee.qrent.billing.task.persistence.repository.TaskRunResultRepository;
import ee.qrent.billing.task.persistence.repository.spring.TaskRunResultSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TaskRunResultRepositoryImpl implements TaskRunResultRepository {

  private final TaskRunResultSpringDataRepository springDataRepository;

  @Override
  public List<TaskRunResultJakartaEntity> findAllByTaskTame(final String taskName) {

    return springDataRepository.findAllByTaskName(taskName);
  }

  @Override
  public TaskRunResultJakartaEntity save(TaskRunResultJakartaEntity entity) {

    return springDataRepository.save(entity);
  }
}
