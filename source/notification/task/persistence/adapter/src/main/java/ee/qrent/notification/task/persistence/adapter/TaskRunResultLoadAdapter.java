package ee.qrent.notification.task.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.notification.task.api.out.TaskRunResultLoadPort;
import ee.qrent.notification.task.domain.TaskRunResult;
import java.util.List;

import ee.qrent.notification.task.persistence.mapper.TaskRunResultAdapterMapper;
import ee.qrent.notification.task.persistence.repository.TaskRunResultRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskRunResultLoadAdapter implements TaskRunResultLoadPort {

  private final TaskRunResultRepository repository;
  private final TaskRunResultAdapterMapper mapper;

  @Override
  public List<TaskRunResult> loadAll() {

    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public TaskRunResult loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<TaskRunResult> loadAllByTaskName(final String taskName) {

    return repository.findAllByTaskName(taskName).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }

  @Override
  public List<String> loadAllTaskNames() {

    return repository.findAllTaskNames();
  }
}
