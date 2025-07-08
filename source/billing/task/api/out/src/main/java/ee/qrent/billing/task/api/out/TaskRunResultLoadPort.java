package ee.qrent.billing.task.api.out;

import ee.qrent.billing.task.domain.TaskRunResult;
import ee.qrent.common.out.port.LoadPort;

import java.util.List;

public interface TaskRunResultLoadPort extends LoadPort<TaskRunResult> {
  List<TaskRunResult> loadAllByTaskName(final String taskName);

  List<String> loadAllTaskNames();
}
