package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.AUTHORIZATION_ROOT_PATH;

import ee.qrent.billing.contract.api.in.query.GetAuthorizationQuery;
import ee.qrent.billing.contract.api.in.request.AuthorizationAddRequest;
import ee.qrent.billing.contract.api.in.request.AuthorizationDeleteRequest;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationAddUseCase;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationDeleteUseCase;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(AUTHORIZATION_ROOT_PATH)
@AllArgsConstructor
public class AuthorizationUseCaseController {

  private final GetAuthorizationQuery authorizationBoltQuery;
  private final AuthorizationAddUseCase addUseCase;
  private final AuthorizationDeleteUseCase deleteUseCase;
  private final GetDriverQuery driverQuery;

  @GetMapping(value = "/add-form/{driverId}")
  public String addForm(@PathVariable("driverId") long driverId, final Model model) {
    addAddRequestToModel(model, driverId);
    addDriverInfoToModel(model, driverId);

    return "forms/addAuthorizationBolt";
  }

  private void addAddRequestToModel(Model model, final Long driverId) {
    final var addRequest = new AuthorizationAddRequest();
    addRequest.setDriverId(driverId);

    model.addAttribute("addRequest", addRequest);
  }

  @PostMapping(value = "/add")
  public String addDriver(
          @ModelAttribute final AuthorizationAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);
      addDriverInfoToModel(model, addRequest.getDriverId());

      return "forms/addAuthorizationBolt";
    }

    return "redirect:" + AUTHORIZATION_ROOT_PATH;
  }

  private void addDriverInfoToModel(final Model model, final Long driverId) {
    final var driver = driverQuery.getById(driverId);
    model.addAttribute("driver", driver);
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new AuthorizationDeleteRequest(id));
    model.addAttribute("objectInfo", authorizationBoltQuery.getObjectInfo(id));

    return "forms/deleteDriver";
  }

  @PostMapping("/delete")
  public String deleteDriver(final AuthorizationDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + AUTHORIZATION_ROOT_PATH;
  }
}
