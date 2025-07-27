package ee.qrent.notification.task.core.service;

import ee.qrent.notification.task.api.in.query.GetTaskRunResultQuery;
import ee.qrent.notification.task.api.in.response.TaskRunResultResponse;
import ee.qrent.notification.task.api.out.TaskRunResultLoadPort;
import ee.qrent.notification.task.core.mapper.TaskRunResultResponseMapper;
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

  @Override
  public List<String> getAllTaskNames() {

    return loadPort.loadAllTaskNames();
  }
}
