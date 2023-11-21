package ee.qrental.ui.controller.contract;

import static ee.qrental.ui.controller.util.ControllerUtils.BOLT_AUTHORIZATION_ROOT_PATH;
import static ee.qrental.ui.controller.util.ControllerUtils.CONTRACT_ROOT_PATH;

import ee.qrental.contract.api.in.query.GetAuthorizationBoltQuery;
import ee.qrental.contract.api.in.request.*;
import ee.qrental.contract.api.in.usecase.AuthorizationBoltAddUseCase;
import ee.qrental.contract.api.in.usecase.AuthorizationBoltDeleteUseCase;
import ee.qrental.contract.api.in.usecase.AuthorizationBoltUpdateUseCase;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.request.DriverDeleteRequest;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(BOLT_AUTHORIZATION_ROOT_PATH)
@AllArgsConstructor
public class AuthorizationBoltUseCaseController {

  private final AuthorizationBoltAddUseCase addUseCase;
  private final AuthorizationBoltUpdateUseCase updateUseCase;
  private final AuthorizationBoltDeleteUseCase deleteUseCase;
  private final GetAuthorizationBoltQuery contractQuery;
  private final GetDriverQuery driverQuery;
  private final GetFirmQuery firmQuery;

  @GetMapping(value = "/add-form/{driverId}")
  public String addForm(
      @PathVariable("driverId") long driverId,
      final Model model) {
    addAddRequestToModel(model, driverId, qFirmId);
    addQFirmInfoToModel(model, qFirmId);
    addDriverInfoToModel(model, driverId);

    return "forms/addAuthorizationBolt";
  }

  private void addActiveAuthorizationBoltToModel(final Long driverId, final Model model) {
    final var activeAuthorizationBolt = contractQuery.getActiveAuthorizationBoltByDriverId(driverId);
    if (activeAuthorizationBolt == null) {
      model.addAttribute("activeAuthorizationBolt", "not assigned");
      model.addAttribute("activeAuthorizationBoltId", null);

      return;
    }
    model.addAttribute("activeAuthorizationBolt", activeAuthorizationBolt.getNumber());
    model.addAttribute("activeAuthorizationBoltId", activeAuthorizationBolt.getId());
  }

  private void addAddRequestToModel(Model model, final Long driverId, final Long qFirmId) {
    final var addRequest = new AuthorizationBoltAddRequest();
    addRequest.setDriverId(driverId);
    addRequest.setQFirmId(qFirmId);

    model.addAttribute("addRequest", addRequest);
  }

  @PostMapping(value = "/add")
  public String addDriver(@ModelAttribute final AuthorizationBoltAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);
      addQFirmInfoToModel(model, addRequest.getQFirmId());
      addDriverInfoToModel(model, addRequest.getDriverId());

      return "forms/addAuthorizationBolt";
    }

    return "redirect:" + CONTRACT_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    return "forms/updateDriver";
  }

  private void addQFirmInfoToModel(final Model model, final Long qFirmId) {
    final var qFirm = firmQuery.getById(qFirmId);

    model.addAttribute("qFirm", qFirm);
  }

  private void addDriverInfoToModel(final Model model, final Long driverId) {
    final var driver = driverQuery.getById(driverId);
    model.addAttribute("driver", driver);
  }

  @PostMapping("/update")
  public String updateDriver(final AuthorizationBoltUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);

      return "forms/updateAuthorizationBolt";
    }
    return "redirect:" + CONTRACT_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new DriverDeleteRequest(id));
    model.addAttribute("objectInfo", driverQuery.getObjectInfo(id));

    return "forms/deleteDriver";
  }

  @PostMapping("/delete")
  public String deleteDriver(final AuthorizationBoltDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + CONTRACT_ROOT_PATH;
  }
}
