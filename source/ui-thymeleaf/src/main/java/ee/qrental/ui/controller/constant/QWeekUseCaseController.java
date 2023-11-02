package ee.qrental.ui.controller.constant;

import static ee.qrental.ui.controller.util.ControllerUtils.CONSTANT_ROOT_PATH;
import static ee.qrental.ui.controller.util.ControllerUtils.WEEK_ROOT_PATH;

import ee.qrental.constant.api.in.query.constant.GetConstantQuery;
import ee.qrental.constant.api.in.query.qweek.GetQWeekQuery;
import ee.qrental.constant.api.in.request.constant.ConstantAddRequest;
import ee.qrental.constant.api.in.request.constant.ConstantDeleteRequest;
import ee.qrental.constant.api.in.request.constant.ConstantUpdateRequest;
import ee.qrental.constant.api.in.request.qweek.QWeekAddRequest;
import ee.qrental.constant.api.in.usecase.constant.ConstantAddUseCase;
import ee.qrental.constant.api.in.usecase.constant.ConstantDeleteUseCase;
import ee.qrental.constant.api.in.usecase.constant.ConstantUpdateUseCase;
import ee.qrental.constant.api.in.usecase.qweek.QWeekAddUseCase;
import ee.qrental.constant.api.in.usecase.qweek.QWeekDeleteUseCase;
import ee.qrental.constant.api.in.usecase.qweek.QWeekUpdateUseCase;
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
  public String addConstantConstant(@ModelAttribute final ConstantAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addConstant";
    }

    return "redirect:" + CONSTANT_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", constantQuery.getUpdateRequestById(id));

    return "forms/updateConstant";
  }

  @PostMapping("/update")
  public String updateConstantConstant(final ConstantUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);

      return "forms/updateConstant";
    }

    return "redirect:" + CONSTANT_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new ConstantDeleteRequest(id));
    model.addAttribute("objectInfo", constantQuery.getObjectInfo(id));

    return "forms/deleteConstant";
  }

  @PostMapping("/delete")
  public String deleteForm(final ConstantDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteConstant";
    }
    return "redirect:" + CONSTANT_ROOT_PATH;
  }
}
