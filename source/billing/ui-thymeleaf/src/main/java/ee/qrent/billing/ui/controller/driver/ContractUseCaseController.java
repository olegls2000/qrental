package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.CONTRACT_ROOT_PATH;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.request.ContractAddRequest;
import ee.qrent.billing.contract.api.in.request.ContractCloseRequest;
import ee.qrent.billing.contract.api.in.response.ContractPreCloseResponse;
import ee.qrent.billing.contract.api.in.usecase.ContractAddUseCase;
import ee.qrent.billing.contract.api.in.usecase.ContractCloseUseCase;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CONTRACT_ROOT_PATH)
@AllArgsConstructor
public class ContractUseCaseController {

  private final ContractAddUseCase addUseCase;
  private final ContractCloseUseCase closeUseCase;
  private final GetContractQuery contractQuery;
  private final GetDriverQuery driverQuery;
  private final GetFirmQuery firmQuery;
  private final QDateTime qDateTime;

  @GetMapping(value = "/add-form/{driverId}/{qFirmId}")
  public String addForm(
      @PathVariable("driverId") long driverId,
      @PathVariable("qFirmId") long qFirmId,
      final Model model) {
    addAddRequestToModel(model, driverId, qFirmId);
    addQFirmInfoToModel(model, qFirmId);
    addDriverInfoToModel(model, driverId);
    addContractDurationsToModel(model);

    return "forms/addContract";
  }

  @PostMapping(value = "/add")
  public String addDriver(@ModelAttribute final ContractAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);
      addQFirmInfoToModel(model, addRequest.getQFirmId());
      addDriverInfoToModel(model, addRequest.getDriverId());
      addContractDurationsToModel(model);

      return "forms/addContract";
    }

    return "redirect:" + CONTRACT_ROOT_PATH + "/active";
  }

  @GetMapping(value = "/close-form/{id}")
  public String closeForm(@PathVariable("id") long id, final Model model) {
    final var preCloseResponse = closeUseCase.getPreCloseResponse(id);
    addPreCloseResponseToModel(model, preCloseResponse);
    final var closeRequest = getCloseRequest(id);
    addCloseRequestToModel(model, closeRequest);

    return "forms/closeContract";
  }

  @PostMapping("/close")
  public String close(final ContractCloseRequest closeRequest, final Model model) {
    closeUseCase.close(closeRequest);
    if (closeRequest.hasViolations()) {
      addCloseRequestToModel(model, closeRequest);
      final var preCloseResponse = closeUseCase.getPreCloseResponse(closeRequest.getId());
      addPreCloseResponseToModel(model, preCloseResponse);

      return "forms/closeContract";
    }

    return "redirect:" + CONTRACT_ROOT_PATH + "/closed";
  }

  private static void addCloseRequestToModel(
      final Model model, final ContractCloseRequest closeRequest) {
    model.addAttribute("closeRequest", closeRequest);
  }

  private static void addPreCloseResponseToModel(
      final Model model, final ContractPreCloseResponse preCloseResponse) {
    model.addAttribute("preCloseResponse", preCloseResponse);
  }

  private ContractCloseRequest getCloseRequest(final Long id) {
    final var closeRequest = new ContractCloseRequest();
    closeRequest.setId(id);

    return closeRequest;
  }

  private void addAddRequestToModel(Model model, final Long driverId, final Long qFirmId) {
    final var addRequest = new ContractAddRequest();
    addRequest.setDriverId(driverId);
    addRequest.setQFirmId(qFirmId);
    addRequest.setDateStart(qDateTime.getToday());

    model.addAttribute("addRequest", addRequest);
  }

  private void addQFirmInfoToModel(final Model model, final Long qFirmId) {
    final var qFirm = firmQuery.getById(qFirmId);

    model.addAttribute("qFirm", qFirm);
  }

  private void addDriverInfoToModel(final Model model, final Long driverId) {
    final var driver = driverQuery.getById(driverId);
    model.addAttribute("driver", driver);
  }

  private void addContractDurationsToModel(final Model model) {
    model.addAttribute("contractDurations", contractQuery.getAllDurations());
  }
}
