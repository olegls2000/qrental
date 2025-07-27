package ee.qrent.notification.task.api.in.query;

import ee.qrent.notification.task.api.in.response.TaskRunResultResponse;

import java.util.List;

public interface GetTaskRunResultQuery {
  List<TaskRunResultResponse> getAllByName(final String taskName);

  List<String> getAllTaskNames();
}
