package ee.qrent.billing.ui.controller.task;

import ee.qrent.billing.task.api.in.query.GetTaskRunResultQuery;
import ee.qrent.billing.task.api.in.query.filter.ResultFilter;
import ee.qrent.billing.task.api.in.query.filter.Tasks;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static ee.qrent.billing.ui.controller.ControllerUtils.TASK_ROOT_PATH;

@Controller
@RequestMapping(TASK_ROOT_PATH)
@AllArgsConstructor
public class TaskRunQueryController {

  private final GetTaskRunResultQuery taskRunResultQuery;

  @GetMapping("/results")
  public String getResultsView(final Model model) {
    model.addAttribute("results", taskRunResultQuery.getAll());
    model.addAttribute("states", Tasks.values());
    model.addAttribute("resultFilterRequest", new ResultFilter());

    return "taskRunResults";
  }

  @PostMapping("/results")
  public String getPageWithFilteredResults(
      @ModelAttribute final ResultFilter resultFilterRequest, final Model model) {
    model.addAttribute("results", taskRunResultQuery.getAllByFilter(resultFilterRequest));
    model.addAttribute("states", Tasks.values());
    model.addAttribute("resultFilterRequest", resultFilterRequest);

    return "taskRunResults";
  }
}
