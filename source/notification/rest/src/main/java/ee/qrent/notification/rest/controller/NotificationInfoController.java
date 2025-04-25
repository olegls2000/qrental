package ee.qrent.notification.rest.controller;

import ee.qrent.common.in.usecase.RunTaskUseCase;
import ee.qrent.notification.task.core.EmailNotificationTask;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class NotificationInfoController {

  private final RunTaskUseCase runTaskUseCase;
  private final EmailNotificationTask task;

  @GetMapping("/process")
  public String process() {
    runTaskUseCase.run(task);

    return "Processing triggered, please check a log file";
  }
}
