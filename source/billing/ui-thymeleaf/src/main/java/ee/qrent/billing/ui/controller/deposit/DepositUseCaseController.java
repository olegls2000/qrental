package ee.qrent.billing.ui.controller.deposit;

import ee.qrent.billing.deposit.api.in.query.GetDepositQuery;
import ee.qrent.billing.deposit.api.in.request.DepositAddRequest;
import ee.qrent.billing.deposit.api.in.request.DepositDeleteRequest;
import ee.qrent.billing.deposit.api.in.request.DepositUpdateRequest;
import ee.qrent.billing.deposit.api.in.usecase.DepositAddUseCase;
import ee.qrent.billing.deposit.api.in.usecase.DepositDeleteUseCase;
import ee.qrent.billing.deposit.api.in.usecase.DepositUpdateUseCase;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.ui.controller.AbstractUseCaseController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static ee.qrent.billing.ui.controller.ControllerUtils.*;

@Controller
@RequestMapping(DEPOSIT_ROOT_PATH)
@AllArgsConstructor
public class DepositUseCaseController
    extends AbstractUseCaseController<
        DepositAddRequest, DepositUpdateRequest, DepositDeleteRequest> {

  private final DepositAddUseCase addUseCase;
  private final DepositUpdateUseCase updateUseCase;
  private final DepositDeleteUseCase deleteUseCase;
  private final GetDepositQuery query;
  private final GetDriverQuery driverQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(model, new DepositAddRequest());
    addDriversToModel(model);

    return "forms/addDeposit";
  }

  @PostMapping(value = "/add")
  public String add(@ModelAttribute final DepositAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(model, addRequest);
      addDriversToModel(model);

      return "forms/addDeposit";
    }

    return "redirect:" + DEPOSIT_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    addUpdateRequestToModel(model, query.getUpdateRequestById(id));
    addDriversToModel(model);

    return "forms/updateDeposit";
  }

  @PostMapping("/update")
  public String update(final DepositUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(model, updateRequest);
      addDriversToModel(model);

      return "forms/updateDeposit";
    }

    return "redirect:" + DEPOSIT_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    addDeleteRequestToModel(model, new DepositDeleteRequest(id));
    addObjectInfoToModel(model, query.getObjectInfo(id));

    return "forms/deleteDeposit";
  }

  @PostMapping("/delete")
  public String delete(final DepositDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);

    if (deleteRequest.hasViolations()) {
      addDeleteRequestToModel(model, deleteRequest);
      addObjectInfoToModel(model, query.getObjectInfo(deleteRequest.getId()));

      return "forms/deleteDeposit";
    }

    return "redirect:" + DEPOSIT_ROOT_PATH;
  }

  private void addDriversToModel(final Model model) {
    model.addAttribute("drivers", driverQuery.getAll());
  }
}
