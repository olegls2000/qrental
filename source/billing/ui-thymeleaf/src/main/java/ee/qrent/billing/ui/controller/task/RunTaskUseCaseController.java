package ee.qrent.billing.ui.controller.task;

import ee.qrent.billing.task.api.in.usecase.TaskRunUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrent.billing.ui.controller.ControllerUtils.TASK_ROOT_PATH;

@Controller
@RequestMapping(TASK_ROOT_PATH)
@AllArgsConstructor
public class RunTaskUseCaseController {

  private final TaskRunUseCase taskRunUseCase;

  @GetMapping
  public String getTasksList() {

    return "qTasks";
  }

  @GetMapping("/q-week-calculation/run")
  public String runQWeekCreationTaskGet() {
    taskRunUseCase.runQWeekCreationTask();
    // TODO replace with web UI
    return "invoiceCalculations";
  }

  @GetMapping("/insurance-calculation/run")
  public String runInsuranceCalculationTask() {
    taskRunUseCase.runInsuranceCalculationTask();
    // TODO replace with web UI
    return "invoiceCalculations";
  }

  @GetMapping("/rent-calculation/run")
  public String runRentCalculationTask() {
    taskRunUseCase.runRentCalculationTask();
    // TODO replace with web UI
    return "invoiceCalculations";
  }

  @GetMapping("/obligation-calculation/run")
  public String runObligationCalculationTask() {
    taskRunUseCase.runObligationCalculationTask();
    // TODO replace with web UI
    return "invoiceCalculations";
  }

  @GetMapping("/weekly-report-monday/run")
  public String runWeeklyReportMondayTask() {
    taskRunUseCase.runWeeklyReportMondayTask();
    // TODO replace with web UI
    return "invoiceCalculations";
  }
}
