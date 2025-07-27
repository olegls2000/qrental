package ee.qrent.notification.task.core.service;

import ee.qrent.notification.task.api.in.usecase.TaskRunUseCase;
import ee.qrent.common.in.usecase.QTaskRunner;
import ee.qrent.notification.task.core.task.EmailNotificationTask;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TaskRunService implements TaskRunUseCase {

  private final QTaskRunner qTaskRunner;
  private final EmailNotificationTask emailNotificationTask;


  @Override
  public void runEmailNotificationTask() {
    qTaskRunner.run(emailNotificationTask);
  }
}
