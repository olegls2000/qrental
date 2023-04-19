package ee.qrental.ui.controller.constant;

import static ee.qrental.ui.controller.ControllerUtils.CONSTANT_ROOT_PATH;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.request.ConstantAddRequest;
import ee.qrental.constant.api.in.request.ConstantDeleteRequest;
import ee.qrental.constant.api.in.request.ConstantUpdateRequest;
import ee.qrental.constant.api.in.usecase.ConstantAddUseCase;
import ee.qrental.constant.api.in.usecase.ConstantDeleteUseCase;
import ee.qrental.constant.api.in.usecase.ConstantUpdateUseCase;
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
  public String addConstantConstant(@ModelAttribute final ConstantAddRequest constantInfo) {
    addUseCase.add(constantInfo);

    return "redirect:" + CONSTANT_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", constantQuery.getUpdateRequestById(id));

    return "forms/updateConstant";
  }

  @PostMapping("/update")
  public String updateConstantConstant(final ConstantUpdateRequest constantUpdateRequest) {
    updateUseCase.update(constantUpdateRequest);

    return "redirect:" + CONSTANT_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new ConstantDeleteRequest(id));
    model.addAttribute("objectInfo", constantQuery.getObjectInfo(id));

    return "forms/deleteConstant";
  }

  @PostMapping("/delete")
  public String deleteForm(final ConstantDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + CONSTANT_ROOT_PATH;
  }
}
