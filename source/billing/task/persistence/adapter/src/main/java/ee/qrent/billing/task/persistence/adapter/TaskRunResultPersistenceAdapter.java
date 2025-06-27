package ee.qrent.billing.task.persistence.adapter;

import ee.qrent.billing.task.api.out.TaskRunResultAddPort;
import ee.qrent.billing.task.domain.TaskRunResult;
import ee.qrent.billing.task.persistence.mapper.TaskRunResultAdapterMapper;
import ee.qrent.billing.task.persistence.repository.TaskRunResultRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskRunResultPersistenceAdapter implements TaskRunResultAddPort {

  private final TaskRunResultRepository repository;
  private final TaskRunResultAdapterMapper mapper;

  @Override
  public TaskRunResult add(final TaskRunResult domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }
}
