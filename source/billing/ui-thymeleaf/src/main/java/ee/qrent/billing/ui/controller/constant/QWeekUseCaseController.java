package ee.qrent.billing.ui.controller.constant;

import static ee.qrent.billing.ui.controller.ControllerUtils.WEEK_ROOT_PATH;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.request.QWeekAddRequest;
import ee.qrent.billing.constant.api.in.request.QWeekDeleteRequest;
import ee.qrent.billing.constant.api.in.request.QWeekUpdateRequest;
import ee.qrent.billing.constant.api.in.usecase.QWeekAddUseCase;
import ee.qrent.billing.constant.api.in.usecase.QWeekDeleteUseCase;
import ee.qrent.billing.constant.api.in.usecase.QWeekUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(WEEK_ROOT_PATH)
@AllArgsConstructor
public class QWeekUseCaseController {

  private final QWeekAddUseCase addUseCase;
  private final QWeekUpdateUseCase updateUseCase;
  private final QWeekDeleteUseCase deleteUseCase;
  private final GetQWeekQuery weekQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new QWeekAddRequest());

    return "forms/addQWeek";
  }

  @PostMapping(value = "/add")
  public String addQWeekQWeek(@ModelAttribute final QWeekAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addQWeek";
    }

    return "redirect:" + WEEK_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", weekQuery.getUpdateRequestById(id));

    return "forms/updateQWeek";
  }

  @PostMapping("/update")
  public String updateQWeek(final QWeekUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);

      return "forms/updateQWeek";
    }

    return "redirect:" + WEEK_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new QWeekDeleteRequest(id));
    model.addAttribute("objectInfo", weekQuery.getObjectInfo(id));

    return "forms/deleteQWeek";
  }

  @PostMapping("/delete")
  public String deleteForm(final QWeekDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteQWeek";
    }
    return "redirect:" + WEEK_ROOT_PATH;
  }
}
