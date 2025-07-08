package ee.qrent.billing.ui.controller.task;

import ee.qrent.billing.task.api.in.query.GetTaskRunResultQuery;
import ee.qrent.billing.task.api.in.response.TaskRunResultResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

import static ee.qrent.billing.ui.controller.ControllerUtils.TASK_ROOT_PATH;

@Controller
@RequestMapping(TASK_ROOT_PATH)
@AllArgsConstructor
public class TaskRunQueryController {

  private final GetTaskRunResultQuery taskRunResultQuery;

  @GetMapping("/results")
  public String getResultsView(final Model model) {
    addResultToModel(model, Collections.emptyList());
    addTaskNamesToModel(model);
    addFilterToModel(new ResultFilter(), model);

    return "taskRunResults";
  }

  private void addResultToModel(final Model model, final List<TaskRunResultResponse> results) {
    model.addAttribute("results", results);
  }

  @PostMapping("/results")
  public String getPageWithFilteredResults(
      @ModelAttribute final ResultFilter resultFilterRequest, final Model model) {
    addResultToModel(model, taskRunResultQuery.getAllByName(resultFilterRequest.getTaskName()));
    addTaskNamesToModel(model);
    addFilterToModel(resultFilterRequest, model);

    return "taskRunResults";
  }

  private void addFilterToModel(final ResultFilter resultFilterRequest, final Model model) {
    model.addAttribute("resultFilterRequest", resultFilterRequest);
  }

  private void addTaskNamesToModel(final Model model) {
    model.addAttribute("taskNames", taskRunResultQuery.getAllTaskNames());
  }
}
