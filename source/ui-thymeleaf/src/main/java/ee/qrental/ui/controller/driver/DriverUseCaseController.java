package ee.qrental.ui.controller.driver;

import static ee.qrental.ui.controller.ControllerUtils.DRIVER_ROOT_PATH;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.request.DriverAddRequest;
import ee.qrental.driver.api.in.request.DriverDeleteRequest;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.in.usecase.DriverAddUseCase;
import ee.qrental.driver.api.in.usecase.DriverDeleteUseCase;
import ee.qrental.driver.api.in.usecase.DriverUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(DRIVER_ROOT_PATH)
@AllArgsConstructor
public class DriverUseCaseController {

  private final DriverAddUseCase addUseCase;
  private final DriverUpdateUseCase updateUseCase;
  private final DriverDeleteUseCase deleteUseCase;
  private final GetDriverQuery driverQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new DriverAddRequest());

    return "forms/addDriver";
  }

  @PostMapping(value = "/add")
  public String addDriverDriver(@ModelAttribute final DriverAddRequest driverInfo) {
    addUseCase.add(driverInfo);

    return "redirect:" + DRIVER_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", driverQuery.getUpdateRequestById(id));

    return "forms/updateDriver";
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
  public String deleteForm(final DriverDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + DRIVER_ROOT_PATH;
  }
}
