package ee.qrent.billing.task.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.task.api.out.TaskRunResultLoadPort;
import ee.qrent.billing.task.domain.TaskRunResult;
import java.util.List;

import ee.qrent.billing.task.persistence.mapper.TaskRunResultAdapterMapper;
import ee.qrent.billing.task.persistence.repository.TaskRunResultRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskRunResultLoadAdapter implements TaskRunResultLoadPort {

  private final TaskRunResultRepository repository;
  private final TaskRunResultAdapterMapper mapper;

  @Override
  public List<TaskRunResult> loadAllByTaskName(final String taskName) {

    return repository.findAllByTaskTame(taskName).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }
}
