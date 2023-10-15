package ee.qrental.ui.controller.link;

import static ee.qrental.ui.controller.util.ControllerUtils.CAR_LINK_ROOT_PATH;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.in.request.CarLinkAddRequest;
import ee.qrental.car.api.in.request.CarLinkDeleteRequest;
import ee.qrental.car.api.in.request.CarLinkUpdateRequest;
import ee.qrental.car.api.in.usecase.CarLinkAddUseCase;
import ee.qrental.car.api.in.usecase.CarLinkDeleteUseCase;
import ee.qrental.car.api.in.usecase.CarLinkUpdateUseCase;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CAR_LINK_ROOT_PATH)
@AllArgsConstructor
public class CarLinkUseCaseController {

  private final CarLinkAddUseCase addUseCase;
  private final CarLinkUpdateUseCase updateUseCase;
  private final CarLinkDeleteUseCase deleteUseCase;
  private final GetCarLinkQuery carLinkQuery;
  private final GetCarQuery carQuery;
  private final GetDriverQuery driverQuery;

  @GetMapping(value = "/add-form/{driverId}")
  public String addForm(@PathVariable("driverId") long driverId, final Model model) {
    final var addRequest = new CarLinkAddRequest();
    addRequest.setDriverId(driverId);
    model.addAttribute("addRequest", addRequest);
    addDriverInfoToModel(driverId, model);
    addCarListToModel(model);

    return "forms/addCarLink";
  }

  @PostMapping(value = "/add")
  public String addCarLink(@ModelAttribute final CarLinkAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);

    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addCarLink";
    }

    return "redirect:" + CAR_LINK_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    final var updateRequest = carLinkQuery.getUpdateRequestById(id);
    final var driverId = updateRequest.getDriverId();
    model.addAttribute("updateRequest", carLinkQuery.getUpdateRequestById(id));
    addDriverInfoToModel(driverId, model);
    addCarListToModel(model);

    return "forms/updateCarLink";
  }

  @PostMapping("/update")
  public String updateLinkLink(final CarLinkUpdateRequest linkUpdateRequest) {
    updateUseCase.update(linkUpdateRequest);

    return "redirect:" + CAR_LINK_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new CarLinkDeleteRequest(id));
    model.addAttribute("objectInfo", carLinkQuery.getObjectInfo(id));

    return "forms/deleteCarLink";
  }

  @PostMapping("/delete")
  public String deleteForm(final CarLinkDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + CAR_LINK_ROOT_PATH;
  }

  private void addCarListToModel(final Model model) {
    final var cars = carQuery.getAll();
    model.addAttribute("cars", cars);
  }

  private void addDriverInfoToModel(final Long driverId, final Model model) {
    final var driver = driverQuery.getById(driverId);
    model.addAttribute("driver", driver);
  }
}
