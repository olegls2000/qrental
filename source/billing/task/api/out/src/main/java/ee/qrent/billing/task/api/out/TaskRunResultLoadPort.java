package ee.qrent.billing.task.api.out;

import ee.qrent.billing.task.domain.TaskRunResult;

import java.util.List;

public interface TaskRunResultLoadPort {
  List<TaskRunResult> loadAllByTaskName(final String taskName);
}
