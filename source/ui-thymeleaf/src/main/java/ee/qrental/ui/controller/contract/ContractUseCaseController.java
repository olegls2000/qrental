package ee.qrental.ui.controller.contract;

import static ee.qrental.ui.controller.util.ControllerUtils.CONTRACT_ROOT_PATH;

import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.contract.api.in.request.ContractAddRequest;
import ee.qrental.contract.api.in.request.ContractDeleteRequest;
import ee.qrental.contract.api.in.request.ContractUpdateRequest;
import ee.qrental.contract.api.in.usecase.ContractAddUseCase;
import ee.qrental.contract.api.in.usecase.ContractDeleteUseCase;
import ee.qrental.contract.api.in.usecase.ContractUpdateUseCase;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.request.DriverDeleteRequest;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CONTRACT_ROOT_PATH)
@AllArgsConstructor
public class ContractUseCaseController {

  private final ContractAddUseCase addUseCase;
  private final ContractUpdateUseCase updateUseCase;
  private final ContractDeleteUseCase deleteUseCase;
  private final GetContractQuery contractQuery;
  private final GetDriverQuery driverQuery;
  private final GetFirmQuery firmQuery;

  @GetMapping(value = "/add-form/{driverId}/{qFirmId}")
  public String addForm(
      @PathVariable("driverId") long driverId,
      @PathVariable("qFirmId") long qFirmId,
      final Model model) {
    addAddRequestToModel(model, driverId, qFirmId);
    addQFirmInfoToModel(model, qFirmId);
    addDriverInfoToModel(model, driverId);

    return "forms/addContract";
  }

  private void addActiveContractToModel(final Long driverId, final Model model) {
    final var activeContract = contractQuery.getActiveContractByDriverId(driverId);
    if (activeContract == null) {
      model.addAttribute("activeContract", "not assigned");
      model.addAttribute("activeContractId", null);

      return;
    }
    model.addAttribute("activeContract", activeContract.getNumber());
    model.addAttribute("activeContractId", activeContract.getId());
  }

  private void addAddRequestToModel(Model model, final Long driverId, final Long qFirmId) {
    final var addRequest = new ContractAddRequest();
    addRequest.setDriverId(driverId);
    addRequest.setQFirmId(qFirmId);

    model.addAttribute("addRequest", addRequest);
  }

  @PostMapping(value = "/add")
  public String addDriver(@ModelAttribute final ContractAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);
      addQFirmInfoToModel(model, addRequest.getQFirmId());
      addDriverInfoToModel(model, addRequest.getDriverId());

      return "forms/addContract";
    }

    return "redirect:" + CONTRACT_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    return "forms/updateDriver";
  }

  private void addQFirmInfoToModel(final Model model, final Long qFirmId) {
    final var qFirm = firmQuery.getById(qFirmId);

    model.addAttribute("qFirm", qFirm);
  }

  private void addDriverInfoToModel(final Model model, final Long driverId) {
    final var driver = driverQuery.getById(driverId);
    model.addAttribute("driver", driver);
  }

  @PostMapping("/update")
  public String updateDriver(final ContractUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);

      return "forms/updateContract";
    }
    return "redirect:" + CONTRACT_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new DriverDeleteRequest(id));
    model.addAttribute("objectInfo", driverQuery.getObjectInfo(id));

    return "forms/deleteDriver";
  }

  @PostMapping("/delete")
  public String deleteDriver(final ContractDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + CONTRACT_ROOT_PATH;
  }
}
