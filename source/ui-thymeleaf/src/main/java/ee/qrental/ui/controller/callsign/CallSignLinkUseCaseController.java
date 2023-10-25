package ee.qrental.ui.controller.callsign;

import static ee.qrental.ui.controller.util.ControllerUtils.BALANCE_ROOT_PATH;
import static ee.qrental.ui.controller.util.ControllerUtils.CALL_SIGN_LINK_ROOT_PATH;

import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetCallSignQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.request.CallSignLinkAddRequest;
import ee.qrental.driver.api.in.request.CallSignLinkDeleteRequest;
import ee.qrental.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.driver.api.in.usecase.CallSignLinkAddUseCase;
import ee.qrental.driver.api.in.usecase.CallSignLinkDeleteUseCase;
import ee.qrental.driver.api.in.usecase.CallSignLinkUpdateUseCase;
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

  private void addAvailableCallSignsToModel(final Model model) {
    final var callSigns = callSignQuery.getAvailable();
    model.addAttribute("callSigns", callSigns);
  }

  @GetMapping(value = "/add-form/{id}")
  public String addForm(final Model model, @PathVariable("id") long driverId) {
    final var addRequest = new CallSignLinkAddRequest();
    addRequest.setDriverId(driverId);
    addAddRequestToModel(model, addRequest);
    addDriverInfoToModel(driverId, model);
    addAvailableCallSignsToModel(model);

    return "forms/addCallSignLink";
  }

  private void addDriverInfoToModel(final Long driverId, final Model model) {
    final var driver = driverQuery.getById(driverId);
    model.addAttribute("driver", driver);
  }

  @PostMapping(value = "/add")
  public String addCallSignLink(
      @ModelAttribute final CallSignLinkAddRequest addRequest, final Model model) {

    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(model, addRequest);
      addDriverInfoToModel(addRequest.getDriverId(), model);
      addAvailableCallSignsToModel(model);

      return "forms/addCallSignLink";
    }

    return "redirect:" + CALL_SIGN_LINK_ROOT_PATH;
  }

  private void addAddRequestToModel(final Model model, final CallSignLinkAddRequest addRequest) {
    model.addAttribute("addRequest", addRequest);
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(final Model model, @PathVariable("id") long id) {
    final var updateRequest = callSignLinkQuery.getUpdateRequestById(id);
    final var driverId = updateRequest.getDriverId();
    final var currentCallSign = callSignQuery.getById(updateRequest.getCallSignId()).getCallSign();
    addUpdateRequestToModel(model, updateRequest);
    addDriverInfoToModel(driverId, model);
    addAvailableCallSignsToModel(model);
    model.addAttribute("currentCallSign", currentCallSign);

    return "forms/updateCallSignLink";
  }

  @PostMapping("/update")
  public String updateCallSignCallSignLink(
      final Model model,
      final CallSignLinkUpdateRequest updateRequest) {

    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(model, updateRequest);
      addAvailableCallSignsToModel(model);

      return "forms/updateCallSignLink";
    }
    final var redirectPage = BALANCE_ROOT_PATH + "/driver/" + updateRequest.getDriverId();

    return "redirect:" + redirectPage;
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
