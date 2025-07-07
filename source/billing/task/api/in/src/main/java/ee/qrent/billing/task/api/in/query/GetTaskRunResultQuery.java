package ee.qrent.billing.task.api.in.query;

import ee.qrent.billing.task.api.in.response.TaskRunResultResponse;
import ee.qrent.billing.task.api.in.query.filter.ResultFilter;
import ee.qrent.common.in.query.BaseGetQuery;

import java.util.List;

public interface GetTaskRunResultQuery extends BaseGetQuery<Object, TaskRunResultResponse> {
  List<TaskRunResultResponse> getAllByName(final String taskName);

  List<TaskRunResultResponse> getAllByFilter(final ResultFilter filterRequest);
}
