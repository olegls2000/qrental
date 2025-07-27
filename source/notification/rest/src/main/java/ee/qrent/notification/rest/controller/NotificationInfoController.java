package ee.qrent.notification.rest.controller;

import ee.qrent.notification.task.api.in.usecase.TaskRunUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class NotificationInfoController {

  private final TaskRunUseCase taskRunUseCase;

  @GetMapping("/process")
  public String process() {
    taskRunUseCase.runEmailNotificationTask();

    return "Processing triggered, please check a log file";
  }
}
