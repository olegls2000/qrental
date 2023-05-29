package ee.qrental.ui.controller.driver;

import static ee.qrental.ui.controller.ControllerUtils.DRIVER_ROOT_PATH;

import ee.qrental.driver.api.in.query.GetCallSignQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.request.DriverAddRequest;
import ee.qrental.driver.api.in.request.DriverDeleteRequest;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.in.response.CallSignResponse;
import ee.qrental.driver.api.in.usecase.DriverAddUseCase;
import ee.qrental.driver.api.in.usecase.DriverDeleteUseCase;
import ee.qrental.driver.api.in.usecase.DriverUpdateUseCase;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(DRIVER_ROOT_PATH)
@AllArgsConstructor
public class DriverUseCaseController {

  private static final String CALL_SIGN_OPTIONS = "callSigns";

  private final DriverAddUseCase addUseCase;
  private final DriverUpdateUseCase updateUseCase;
  private final DriverDeleteUseCase deleteUseCase;
  private final GetDriverQuery driverQuery;
  private final GetFirmQuery firmQuery;
  private final GetCallSignQuery callSignQuery;

  private static void addUpdateRequestToModel(Model model, DriverUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(model);
    addQFirmsToModel(model);
    addCallSignOptionsToModel(model);

    return "forms/addDriver";
  }

  private void addAddRequestToModel(Model model) {
    model.addAttribute("addRequest", new DriverAddRequest());
  }

  private void addCallSignOptionsToModel(Model model) {
    model.addAttribute(CALL_SIGN_OPTIONS, callSignQuery.getAvailable());
  }

  @PostMapping(value = "/add")
  public String addDriver(@ModelAttribute final DriverAddRequest addRequest) {
    addUseCase.add(addRequest);

    return "redirect:" + DRIVER_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    final var updateRequest = driverQuery.getUpdateRequestById(id);
    addUpdateRequestToModel(model, updateRequest);
    addQFirmsToModel(model);
    addCallSignOptionsToModel(model, updateRequest);

    return "forms/updateDriver";
  }

  private void addCallSignOptionsToModel(
      final Model model, final DriverUpdateRequest updateRequest) {
    final var availableCallSigns = callSignQuery.getAvailable();
    final var callSignId = updateRequest.getCallSignId();
    if (callSignId != null) {
      final var currentCallSign =
          CallSignResponse.builder().id(callSignId).callSign(updateRequest.getCallSign()).build();
      availableCallSigns.add(currentCallSign);
    }
    model.addAttribute(CALL_SIGN_OPTIONS, availableCallSigns);
  }

  private void addQFirmsToModel(Model model) {
    model.addAttribute("qFirms", firmQuery.getAll());
  }

  @PostMapping("/update")
  public String updateDriver(final DriverUpdateRequest driverUpdateRequest) {
    updateUseCase.update(driverUpdateRequest);

    return "redirect:" + DRIVER_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new DriverDeleteRequest(id));
    model.addAttribute("objectInfo", driverQuery.getObjectInfo(id));

    return "forms/deleteDriver";
  }

  @PostMapping("/delete")
  public String deleteDriver(final DriverDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + DRIVER_ROOT_PATH;
  }
}
