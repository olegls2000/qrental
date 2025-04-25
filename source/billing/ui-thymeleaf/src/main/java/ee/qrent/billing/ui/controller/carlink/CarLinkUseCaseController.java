package ee.qrent.billing.ui.controller.carlink;

import static ee.qrent.billing.ui.controller.ControllerUtils.*;

import ee.qrent.billing.car.api.in.query.GetCarLinkQuery;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.api.in.request.CarLinkAddRequest;
import ee.qrent.billing.car.api.in.request.CarLinkCloseRequest;
import ee.qrent.billing.car.api.in.request.CarLinkDeleteRequest;
import ee.qrent.billing.car.api.in.request.CarLinkUpdateRequest;
import ee.qrent.billing.car.api.in.usecase.CarLinkAddUseCase;
import ee.qrent.billing.car.api.in.usecase.CarLinkCloseUseCase;
import ee.qrent.billing.car.api.in.usecase.CarLinkDeleteUseCase;
import ee.qrent.billing.car.api.in.usecase.CarLinkUpdateUseCase;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
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
  private final CarLinkCloseUseCase closeUseCase;
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
  public String add(@ModelAttribute final CarLinkAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);

    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addCarLink";
    }

    return getDriverPortalRedirectUrl(addRequest.getDriverId());
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    final var updateRequest = carLinkQuery.getUpdateRequestById(id);
    final var driverId = updateRequest.getDriverId();
    final var linkedCar = carQuery.getById(updateRequest.getCarId());
    model.addAttribute("updateRequest", carLinkQuery.getUpdateRequestById(id));
    model.addAttribute("linkedCar", linkedCar);
    addDriverInfoToModel(driverId, model);

    final var cars = carQuery.getAvailableCars();
    cars.add(linkedCar);
    model.addAttribute("cars", cars);

    return "forms/updateCarLink";
  }

  @PostMapping("/update")
  public String updateLinkLink(final CarLinkUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);
      addDriverInfoToModel(updateRequest.getDriverId(), model);

      final var linkedCar = carQuery.getById(updateRequest.getCarId());
      final var cars = carQuery.getAvailableCars();
      cars.add(linkedCar);
      model.addAttribute("cars", cars);

      return "forms/updateCarLink";
    }

    return getDriverPortalRedirectUrl(updateRequest.getDriverId());
  }

  @GetMapping(value = "/close-form/{id}/driver/{driverId}")
  public String closeForm(
      final Model model, @PathVariable("id") long id, @PathVariable("driverId") long driverId) {
    model.addAttribute("closeRequest", new CarLinkCloseRequest(id, driverId));
    model.addAttribute("objectInfo", carLinkQuery.getObjectInfo(id));

    return "forms/closeCarLink";
  }

  @PostMapping("/close")
  public String close(final CarLinkCloseRequest closeRequest) {
    final var driverId = closeRequest.getDriverId();
    closeUseCase.close(closeRequest);

    return getDriverPortalRedirectUrl(driverId);
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(final Model model, @PathVariable("id") long id) {
    model.addAttribute("deleteRequest", new CarLinkDeleteRequest(id));
    model.addAttribute("objectInfo", carLinkQuery.getObjectInfo(id));

    return "forms/deleteCarLink";
  }

  @PostMapping("/delete")
  public String delete(final CarLinkDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);
      model.addAttribute("objectInfo", carLinkQuery.getObjectInfo(deleteRequest.getId()));

      return "forms/deleteCarLink";
    }

    return "redirect:" + CAR_LINK_ROOT_PATH + "/closed";
  }

  private void addCarListToModel(final Model model) {
    final var cars = carQuery.getAvailableCars();
    model.addAttribute("cars", cars);
  }

  private void addDriverInfoToModel(final Long driverId, final Model model) {
    final var driver = driverQuery.getById(driverId);
    model.addAttribute("driver", driver);
  }

  private static String getDriverPortalRedirectUrl(final Long driverId) {
    return "redirect:" + BALANCE_ROOT_PATH + "/driver/" + driverId;
  }
}
