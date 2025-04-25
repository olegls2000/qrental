package ee.qrent.billing.ui.controller.constant;

import static ee.qrent.billing.ui.controller.ControllerUtils.CONSTANT_ROOT_PATH;

import ee.qrent.billing.constant.api.in.query.GetConstantQuery;
import ee.qrent.billing.constant.api.in.request.ConstantAddRequest;
import ee.qrent.billing.constant.api.in.request.ConstantDeleteRequest;
import ee.qrent.billing.constant.api.in.request.ConstantUpdateRequest;
import ee.qrent.billing.constant.api.in.usecase.ConstantAddUseCase;
import ee.qrent.billing.constant.api.in.usecase.ConstantDeleteUseCase;
import ee.qrent.billing.constant.api.in.usecase.ConstantUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CONSTANT_ROOT_PATH)
@AllArgsConstructor
public class ConstantUseCaseController {

  private final ConstantAddUseCase addUseCase;
  private final ConstantUpdateUseCase updateUseCase;
  private final ConstantDeleteUseCase deleteUseCase;
  private final GetConstantQuery constantQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new ConstantAddRequest());

    return "forms/addConstant";
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
