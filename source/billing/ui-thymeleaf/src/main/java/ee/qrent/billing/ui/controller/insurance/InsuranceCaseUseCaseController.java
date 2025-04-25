package ee.qrent.billing.ui.controller.insurance;

import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseAddRequest;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseCloseRequest;
import ee.qrent.billing.insurance.api.in.request.InsuranceCasePreCloseResponse;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCaseAddUseCase;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCaseCloseUseCase;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCaseUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static ee.qrent.billing.ui.controller.ControllerUtils.*;

@Controller
@RequestMapping(INSURANCE_ROOT_PATH)
@AllArgsConstructor
public class InsuranceCaseUseCaseController {

  private final InsuranceCaseAddUseCase addUseCase;
  private final InsuranceCaseUpdateUseCase updateUseCase;
  private final InsuranceCaseCloseUseCase closeUseCase;
  private final GetInsuranceCaseQuery insuranceCaseQuery;
  private final GetDriverQuery driverQuery;
  private final GetCarQuery carQuery;

  @GetMapping(value = "/cases/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(model);
    addCarsToModel(model);
    addDriversToModel(model);

    return "forms/addInsuranceCase";
  }

  @PostMapping(value = "/cases/add")
  public String add(@ModelAttribute final InsuranceCaseAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute(ADD_REQUEST_ATTRIBUTE, addRequest);
      addCarsToModel(model);
      addDriversToModel(model);

      return "forms/addInsuranceCase";
    }

    return "redirect:" + INSURANCE_ROOT_PATH + "/cases/active";
  }

  @GetMapping(value = "/cases/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    final var updateRequest = insuranceCaseQuery.getUpdateRequestById(id);
    addUpdateRequestToModel(model, updateRequest);
    addCarsToModel(model);
    addDriversToModel(model);

    return "forms/updateInsuranceCase";
  }

  @PostMapping("/cases/update")
  public String update(final InsuranceCaseUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(model, updateRequest);
      addCarsToModel(model);
      addDriversToModel(model);

      return "forms/updateInsuranceCase";
    }
    return "redirect:" + INSURANCE_ROOT_PATH + "/cases/active";
  }

  @GetMapping(value = "/cases/close-form/{id}")
  public String closeForm(@PathVariable("id") long id, final Model model) {
    final var preCloseResponse = closeUseCase.getPreCloseResponse(id);
    addPreCloseResponseToModel(model, preCloseResponse);
    final var closeRequest = getCloseRequest(id);
    addCloseRequestToModel(model, closeRequest);

    return "forms/closeInsuranceCase";
  }

  @PostMapping("/cases/close")
  public String close(final InsuranceCaseCloseRequest closeRequest, final Model model) {
    closeUseCase.close(closeRequest);
    if (closeRequest.hasViolations()) {
      addCloseRequestToModel(model, closeRequest);
      final var preCloseResponse = closeUseCase.getPreCloseResponse(closeRequest.getId());
      addPreCloseResponseToModel(model, preCloseResponse);

      return "forms/closeInsuranceCase";
    }

    return "redirect:" + INSURANCE_ROOT_PATH + "/cases/closed";
  }

  private void addAddRequestToModel(Model model) {
    model.addAttribute(ADD_REQUEST_ATTRIBUTE, new InsuranceCaseAddRequest());
  }

  private static void addUpdateRequestToModel(
      final Model model, final InsuranceCaseUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  private static void addCloseRequestToModel(
      final Model model, final InsuranceCaseCloseRequest closeRequest) {
    model.addAttribute("closeRequest", closeRequest);
  }

  private static void addPreCloseResponseToModel(
      final Model model, final InsuranceCasePreCloseResponse preCloseResponse) {
    model.addAttribute("preCloseResponse", preCloseResponse);
  }

  private void addCarsToModel(final Model model) {
    final var cars = carQuery.getAll();
    model.addAttribute("cars", cars);
  }

  private void addDriversToModel(final Model model) {
    final var drivers = driverQuery.getAll();
    model.addAttribute("drivers", drivers);
  }

  private InsuranceCaseCloseRequest getCloseRequest(final Long id) {
    final var closeRequest = new InsuranceCaseCloseRequest();
    closeRequest.setId(id);
    return closeRequest;
  }
}
