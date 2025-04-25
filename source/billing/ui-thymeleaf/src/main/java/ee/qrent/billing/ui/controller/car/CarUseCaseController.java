package ee.qrent.billing.ui.controller.car;

import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.api.in.request.CarAddRequest;
import ee.qrent.billing.car.api.in.request.CarDeleteRequest;
import ee.qrent.billing.car.api.in.request.CarUpdateRequest;
import ee.qrent.billing.car.api.in.usecase.CarAddUseCase;
import ee.qrent.billing.car.api.in.usecase.CarDeleteUseCase;
import ee.qrent.billing.car.api.in.usecase.CarUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static ee.qrent.billing.ui.controller.ControllerUtils.CAR_ROOT_PATH;

@Controller
@RequestMapping(CAR_ROOT_PATH)
@AllArgsConstructor
public class CarUseCaseController {

  private final CarAddUseCase addUseCase;
  private final CarUpdateUseCase updateUseCase;
  private final CarDeleteUseCase deleteUseCase;
  private final GetCarQuery carQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(model, new CarAddRequest());
    addAStatusesToModel(model);

    return "forms/addCar";
  }

  @PostMapping(value = "/add")
  public String addCarCar(@ModelAttribute final CarAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(model, addRequest);
      addAStatusesToModel(model);

      return "forms/addCar";
    }

    return "redirect:" + CAR_ROOT_PATH;
  }

  private void addAddRequestToModel(final Model model, final CarAddRequest addRequest) {
    model.addAttribute("addRequest", addRequest);
  }

  private void addAStatusesToModel(final Model model) {
    model.addAttribute("statuses", carQuery.getAllStatuses());
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    addUpdateRequestToModel(model, carQuery.getUpdateRequestById(id));
    addAStatusesToModel(model);

    return "forms/updateCar";
  }

  @PostMapping("/update")
  public String updateCar(final CarUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(model, updateRequest);
      addAStatusesToModel(model);

      return "forms/updateCar";
    }

    return "redirect:" + CAR_ROOT_PATH;
  }

  private void addUpdateRequestToModel(final Model model, final CarUpdateRequest updateRequest) {
    model.addAttribute("updateRequest", updateRequest);
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new CarDeleteRequest(id));
    model.addAttribute("objectInfo", carQuery.getObjectInfo(id));

    return "forms/deleteCar";
  }

  @PostMapping("/delete")
  public String deleteForm(final CarDeleteRequest deleteRequest, Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteCar";
    }
    return "redirect:" + CAR_ROOT_PATH;
  }
}
