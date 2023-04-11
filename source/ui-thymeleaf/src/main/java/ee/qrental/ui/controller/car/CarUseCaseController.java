package ee.qrental.ui.controller.car;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.in.request.CarAddRequest;
import ee.qrental.car.api.in.request.CarDeleteRequest;
import ee.qrental.car.api.in.request.CarUpdateRequest;
import ee.qrental.car.api.in.usecase.CarAddUseCase;
import ee.qrental.car.api.in.usecase.CarDeleteUseCase;
import ee.qrental.car.api.in.usecase.CarUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static ee.qrental.ui.controller.ControllerUtils.CAR_ROOT_PATH;

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
    model.addAttribute("addRequest", new CarAddRequest());

    return "forms/addCar";
  }

  @PostMapping(value = "/add")
  public String addCarCar(@ModelAttribute final CarAddRequest carInfo) {
    addUseCase.add(carInfo);

    return "redirect:" + CAR_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", carQuery.getUpdateRequestById(id));

    return "forms/updateCar";
  }

  @PostMapping("/update")
  public String updateCar(final CarUpdateRequest carUpdateRequest) {
    updateUseCase.update(carUpdateRequest);

    return "redirect:" + CAR_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new CarDeleteRequest(id));
    model.addAttribute("objectInfo", carQuery.getObjectInfo(id));

    return "forms/deleteCar";
  }

  @PostMapping("/delete")
  public String deleteForm(final CarDeleteRequest carDeleteRequest) {
    deleteUseCase.delete(carDeleteRequest);

    return "redirect:" + CAR_ROOT_PATH;
  }
}
