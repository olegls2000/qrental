package ee.qrent.billing.ui.controller.task;

import ee.qrent.billing.task.api.in.query.GetTaskRunResultQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrent.billing.ui.controller.ControllerUtils.TASK_ROOT_PATH;

@Controller
@RequestMapping(TASK_ROOT_PATH)
@AllArgsConstructor
public class TaskRunQueryController {

  private final GetTaskRunResultQuery taskRunResultQuery;

  /// "tasks/results"
  @GetMapping("/results")
  public String getResultsView(final Model model) {
    model.addAttribute("results", taskRunResultQuery.getAllByName("QWEEK-CREATION-TASK"));

    return "taskRunResults";
  }
}
