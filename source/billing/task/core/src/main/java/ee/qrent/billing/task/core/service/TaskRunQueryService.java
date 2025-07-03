package ee.qrent.billing.task.core.service;

import ee.qrent.billing.task.api.in.query.GetTaskRunResultQuery;
import ee.qrent.billing.task.api.in.response.TaskRunResultResponse;
import ee.qrent.billing.task.api.out.TaskRunResultLoadPort;
import ee.qrent.billing.task.core.mapper.TaskRunResultResponseMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TaskRunQueryService implements GetTaskRunResultQuery {

  private final TaskRunResultLoadPort loadPort;
  private final TaskRunResultResponseMapper mapper;

  @Override
  public List<TaskRunResultResponse> getAllByName(final String taskName) {

      return loadPort.loadAllByTaskName(taskName).stream().map(mapper::toResponse).toList();
  }
}
