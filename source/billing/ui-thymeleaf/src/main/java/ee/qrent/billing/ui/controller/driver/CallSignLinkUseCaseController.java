package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.*;

import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetCallSignQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.request.CallSignLinkAddRequest;
import ee.qrent.billing.driver.api.in.request.CallSignLinkCloseRequest;
import ee.qrent.billing.driver.api.in.request.CallSignLinkDeleteRequest;
import ee.qrent.billing.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrent.billing.driver.api.in.usecase.CallSignLinkAddUseCase;
import ee.qrent.billing.driver.api.in.usecase.CallSignLinkCloseUseCase;
import ee.qrent.billing.driver.api.in.usecase.CallSignLinkDeleteUseCase;
import ee.qrent.billing.driver.api.in.usecase.CallSignLinkUpdateUseCase;
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
  private final CallSignLinkCloseUseCase closeUseCase;
  private final CallSignLinkDeleteUseCase deleteUseCase;
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
  public String add(@ModelAttribute final CallSignLinkAddRequest addRequest, final Model model) {

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
    final var linkedCallSign = callSignQuery.getById(updateRequest.getCallSignId()).getCallSign();
    addUpdateRequestToModel(model, updateRequest);
    addDriverInfoToModel(driverId, model);
    addAvailableCallSignsToModel(model);
    model.addAttribute("linkedCallSign", linkedCallSign);

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

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(final Model model, @PathVariable("id") long id) {
    model.addAttribute("deleteRequest", new CallSignLinkDeleteRequest(id));
    model.addAttribute("objectInfo", callSignLinkQuery.getObjectInfo(id));

    return "forms/deleteCallSignLink";
  }

  @PostMapping("/delete")
  public String delete(final CallSignLinkDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);
      model.addAttribute("objectInfo", callSignLinkQuery.getObjectInfo(deleteRequest.getId()));

      return "forms/deleteCallSignLink";
    }

    return "redirect:" + CALL_SIGN_LINK_ROOT_PATH + "/closed";
  }

  @GetMapping(value = "/close-form/{id}/driver/{driverId}")
  public String closeForm(
      final Model model, @PathVariable("id") long id, @PathVariable("driverId") long driverId) {
    model.addAttribute("closeRequest", new CallSignLinkCloseRequest(id, driverId));
    model.addAttribute("objectInfo", callSignLinkQuery.getObjectInfo(id));

    return "forms/closeCallSignLink";
  }

  @PostMapping("/close")
  public String close(final CallSignLinkCloseRequest closeRequest) {
    final var driverId = closeRequest.getDriverId();
    closeUseCase.close(closeRequest);

    return getDriverPortalRedirectUrl(driverId);
  }

  private static String getDriverPortalRedirectUrl(final Long driverId) {
    return "redirect:" + BALANCE_ROOT_PATH + "/driver/" + driverId;
  }
}
