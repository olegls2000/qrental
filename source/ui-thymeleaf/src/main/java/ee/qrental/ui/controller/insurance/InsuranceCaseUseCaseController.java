package ee.qrental.ui.controller.insurance;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrental.insurance.api.in.request.InsuranceCaseAddRequest;
import ee.qrental.insurance.api.in.request.InsuranceCaseDeleteRequest;
import ee.qrental.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrental.insurance.api.in.usecase.InsuranceCaseAddUseCase;
import ee.qrental.insurance.api.in.usecase.InsuranceCaseDeleteUseCase;
import ee.qrental.insurance.api.in.usecase.InsuranceCaseUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static ee.qrental.ui.controller.util.ControllerUtils.*;

@Controller
@RequestMapping(INSURANCE_ROOT_PATH)
@AllArgsConstructor
public class InsuranceCaseUseCaseController {

  private final InsuranceCaseAddUseCase addUseCase;
  private final InsuranceCaseUpdateUseCase updateUseCase;
  private final InsuranceCaseDeleteUseCase deleteUseCase;
  private final GetInsuranceCaseQuery insuranceCaseQuery;
  private final GetDriverQuery driverQuery;
  private final GetCarQuery carQuery;

  private static void addUpdateRequestToModel(
      final Model model, final InsuranceCaseUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  private void addCarsToModel(final Model model) {
    final var cars = carQuery.getAll();
    model.addAttribute("cars", cars);
  }

  private void addDriversToModel(final Model model) {
    final var drivers = driverQuery.getAll();
    model.addAttribute("drivers", drivers);
  }

  @GetMapping(value = "/cases/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(model);
    addCarsToModel(model);
    addDriversToModel(model);

    return "forms/addInsuranceCase";
  }

  private void addAddRequestToModel(Model model) {
    model.addAttribute(ADD_REQUEST_ATTRIBUTE, new InsuranceCaseAddRequest());
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

    return "redirect:" + INSURANCE_ROOT_PATH + "/cases";
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
      model.addAttribute("updateRequest", updateRequest);
      addCarsToModel(model);
      addDriversToModel(model);

      return "forms/updateInsuranceCase";
    }
    return "redirect:" + INSURANCE_ROOT_PATH + "/cases";
  }

  @GetMapping(value = "/cases/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new InsuranceCaseDeleteRequest(id));
    model.addAttribute("objectInfo", insuranceCaseQuery.getObjectInfo(id));

    return "forms/deleteInsuranceCase";
  }

  @PostMapping("/cases/delete")
  public String delete(final InsuranceCaseDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);

    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);
      model.addAttribute("objectInfo", insuranceCaseQuery.getObjectInfo(deleteRequest.getId()));

      return "forms/updateInsuranceCase";
    }

    return "redirect:" + INSURANCE_ROOT_PATH + "/cases";
  }
}
