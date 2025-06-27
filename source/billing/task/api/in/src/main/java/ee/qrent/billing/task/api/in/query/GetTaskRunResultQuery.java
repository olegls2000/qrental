package ee.qrent.billing.task.api.in.query;

import ee.qrent.billing.task.api.in.response.TaskRunResultResponse;

import java.util.List;

public interface GetTaskRunResultQuery {
  List<TaskRunResultResponse> getAllByName(final String taskName);
}
