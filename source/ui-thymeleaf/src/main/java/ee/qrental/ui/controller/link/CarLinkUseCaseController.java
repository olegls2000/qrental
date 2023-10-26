package ee.qrental.ui.controller.link;

import static ee.qrental.ui.controller.util.ControllerUtils.BALANCE_ROOT_PATH;
import static ee.qrental.ui.controller.util.ControllerUtils.CAR_LINK_ROOT_PATH;

import ee.qrental.car.api.in.query.GetCarLinkQuery;
import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.in.request.CarLinkAddRequest;
import ee.qrental.car.api.in.request.CarLinkStopRequest;
import ee.qrental.car.api.in.request.CarLinkUpdateRequest;
import ee.qrental.car.api.in.usecase.CarLinkAddUseCase;
import ee.qrental.car.api.in.usecase.CarLinkStopUseCase;
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
  private final CarLinkStopUseCase stopUseCase;
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
    addCarListToModel(model);

    return "forms/updateCarLink";
  }

  @PostMapping("/update")
  public String updateLinkLink(final CarLinkUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);
      addDriverInfoToModel(updateRequest.getDriverId(), model);
      addCarListToModel(model);

      return "forms/updateCarLink";
    }

    return getDriverPortalRedirectUrl(updateRequest.getDriverId());
  }

  @GetMapping(value = "/stop-form/{id}/driver/{driverId}")
  public String stopForm(
      final Model model, @PathVariable("id") long id, @PathVariable("driverId") long driverId) {
    model.addAttribute("stopRequest", new CarLinkStopRequest(id, driverId));
    model.addAttribute("objectInfo", carLinkQuery.getObjectInfo(id));

    return "forms/stopCarLink";
  }

  @PostMapping("/stop")
  public String stop(final CarLinkStopRequest stopRequest) {
    final var driverId = stopRequest.getDriverId();
    stopUseCase.stop(stopRequest);

    return getDriverPortalRedirectUrl(driverId);
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
