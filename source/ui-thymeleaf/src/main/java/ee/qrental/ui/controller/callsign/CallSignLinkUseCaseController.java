package ee.qrental.ui.controller.callsign;

import static ee.qrental.ui.controller.ControllerUtils.CALL_SIGN_LINK_ROOT_PATH;

import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import ee.qrental.callsign.api.in.query.GetCallSignQuery;
import ee.qrental.callsign.api.in.request.CallSignLinkAddRequest;
import ee.qrental.callsign.api.in.request.CallSignLinkDeleteRequest;
import ee.qrental.callsign.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.callsign.api.in.usecase.CallSignLinkAddUseCase;
import ee.qrental.callsign.api.in.usecase.CallSignLinkDeleteUseCase;
import ee.qrental.callsign.api.in.usecase.CallSignLinkUpdateUseCase;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CALL_SIGN_LINK_ROOT_PATH)
@AllArgsConstructor
public class CallSignLinkUseCaseController {

  private final CallSignLinkAddUseCase addUseCase;
  private final CallSignLinkUpdateUseCase updateUseCase;
  private final CallSignLinkDeleteUseCase deleteUseCase;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignQuery callSignQuery;

  private void addDriverListToModel(final Model model) {
    final var drivers = driverQuery.getAll();
    model.addAttribute("drivers", drivers);
  }

  private void addCallSignListToModel(final Model model) {
    final var callSigns = callSignQuery.getAll();
    model.addAttribute("callSigns", callSigns);
  }

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(model, new CallSignLinkAddRequest());
    addDriverListToModel(model);
    addCallSignListToModel(model);

    return "forms/addCallSignLink";
  }

  @PostMapping(value = "/add")
  public String addCallSignLink(
      @ModelAttribute final CallSignLinkAddRequest addRequest, final Model model) {

    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(model, addRequest);
      addDriverListToModel(model);
      addCallSignListToModel(model);

      return "forms/addCallSignLink";
    }

    return "redirect:" + CALL_SIGN_LINK_ROOT_PATH;
  }

  private void addAddRequestToModel(final Model model, final CallSignLinkAddRequest addRequest) {
    model.addAttribute("addRequest", addRequest);
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(final Model model, @PathVariable("id") long id) {
    addUpdateRequestToModel(model, callSignLinkQuery.getUpdateRequestById(id));
    addDriverListToModel(model);
    addCallSignListToModel(model);

    return "forms/updateCallSignLink";
  }

  @PostMapping("/update")
  public String updateCallSignCallSignLink(
      final Model model, final CallSignLinkUpdateRequest updateRequest) {

    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(model, updateRequest);
      addDriverListToModel(model);
      addCallSignListToModel(model);

      return "forms/updateCallSignLink";
    }

    return "redirect:" + CALL_SIGN_LINK_ROOT_PATH;
  }

  private void addUpdateRequestToModel(
      final Model model, final CallSignLinkUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(final Model model, @PathVariable("id") long id) {
    model.addAttribute("deleteRequest", new CallSignLinkDeleteRequest(id));
    model.addAttribute("objectInfo", callSignLinkQuery.getObjectInfo(id));

    return "forms/deleteCallSignLink";
  }

  @PostMapping("/delete")
  public String deleteForm(final CallSignLinkDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + CALL_SIGN_LINK_ROOT_PATH;
  }
}
