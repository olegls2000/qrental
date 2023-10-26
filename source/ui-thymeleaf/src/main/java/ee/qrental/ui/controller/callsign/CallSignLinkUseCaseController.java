package ee.qrental.ui.controller.callsign;

import static ee.qrental.ui.controller.util.ControllerUtils.BALANCE_ROOT_PATH;
import static ee.qrental.ui.controller.util.ControllerUtils.CALL_SIGN_LINK_ROOT_PATH;

import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetCallSignQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.request.CallSignLinkAddRequest;
import ee.qrental.driver.api.in.request.CallSignLinkStopRequest;
import ee.qrental.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.driver.api.in.usecase.CallSignLinkAddUseCase;
import ee.qrental.driver.api.in.usecase.CallSignLinkStopUseCase;
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
  private final CallSignLinkStopUseCase stopUseCase;
  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetDriverQuery driverQuery;
  private final GetCallSignQuery callSignQuery;

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

    return getDriverPortalRedirectUrl(addRequest.getDriverId());
  }

  private void addAvailableCallSignsToModel(final Model model) {
    final var callSigns = callSignQuery.getAvailable();
    model.addAttribute("callSigns", callSigns);
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
      final Model model, final CallSignLinkUpdateRequest updateRequest) {

    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(model, updateRequest);
      addAvailableCallSignsToModel(model);

      return "forms/updateCallSignLink";
    }
    return getDriverPortalRedirectUrl(updateRequest.getDriverId());
  }

  private void addUpdateRequestToModel(
      final Model model, final CallSignLinkUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  @GetMapping(value = "/stop-form/{id}/driver/{driverId}")
  public String stopForm(
      final Model model, @PathVariable("id") long id, @PathVariable("driverId") long driverId) {
    model.addAttribute("stopRequest", new CallSignLinkStopRequest(id, driverId));
    model.addAttribute("objectInfo", callSignLinkQuery.getObjectInfo(id));

    return "forms/stopCallSignLink";
  }

  @PostMapping("/stop")
  public String stop(final CallSignLinkStopRequest stopRequest) {
    final var driverId = stopRequest.getDriverId();
    stopUseCase.stop(stopRequest);

    return getDriverPortalRedirectUrl(driverId);
  }

  private static String getDriverPortalRedirectUrl(final Long driverId) {
    return "redirect:" + BALANCE_ROOT_PATH + "/driver/" + driverId;
  }
}
