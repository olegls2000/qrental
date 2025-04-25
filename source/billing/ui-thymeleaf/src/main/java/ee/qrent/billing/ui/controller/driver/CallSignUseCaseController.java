package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.CALL_SIGN_ROOT_PATH;

import ee.qrent.billing.driver.api.in.query.GetCallSignQuery;
import ee.qrent.billing.driver.api.in.request.CallSignAddRequest;
import ee.qrent.billing.driver.api.in.request.CallSignDeleteRequest;
import ee.qrent.billing.driver.api.in.request.CallSignUpdateRequest;
import ee.qrent.billing.driver.api.in.usecase.CallSignAddUseCase;
import ee.qrent.billing.driver.api.in.usecase.CallSignDeleteUseCase;
import ee.qrent.billing.driver.api.in.usecase.CallSignUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CALL_SIGN_ROOT_PATH)
@AllArgsConstructor
public class CallSignUseCaseController {

  private final CallSignAddUseCase addUseCase;
  private final CallSignUpdateUseCase updateUseCase;
  private final CallSignDeleteUseCase deleteUseCase;
  private final GetCallSignQuery callSignQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new CallSignAddRequest(), model);

    return "forms/addCallSign";
  }

  @PostMapping(value = "/add")
  public String addCallSignLink(
      @ModelAttribute final CallSignAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      return "forms/addCallSign";
    }

    return "redirect:" + CALL_SIGN_ROOT_PATH;
  }

  private void addAddRequestToModel(final CallSignAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    addUpdateRequestToModel(model, callSignQuery.getUpdateRequestById(id));

    return "forms/updateCallSign";
  }

  @PostMapping("/update")
  public String updateCallSignCallSignLink(
      final CallSignUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(model, updateRequest);

      return "forms/updateCallSign";
    }

    return "redirect:" + CALL_SIGN_ROOT_PATH;
  }

  private void addUpdateRequestToModel(
      final Model model, final CallSignUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, Model model) {
    model.addAttribute("deleteRequest", new CallSignDeleteRequest(id));
    model.addAttribute("objectInfo", callSignQuery.getObjectInfo(id));

    return "forms/deleteCallSign";
  }

  @PostMapping("/delete")
  public String deleteForm(final CallSignDeleteRequest deleteRequest,
                           final Model model) {
    deleteUseCase.delete(deleteRequest);

    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteCallSign";
    }

    return "redirect:" + CALL_SIGN_ROOT_PATH;
  }
}
