package ee.qrent.billing.ui.controller.transaction.transaction;

import static ee.qrent.billing.ui.controller.ControllerUtils.TRANSACTION_ROOT_PATH;

import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.request.TransactionDeleteRequest;
import ee.qrent.billing.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrent.billing.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrent.billing.transaction.api.in.usecase.TransactionDeleteUseCase;
import ee.qrent.billing.transaction.api.in.usecase.TransactionUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(TRANSACTION_ROOT_PATH)
@AllArgsConstructor
public class TransactionUseCaseController {

  private final TransactionAddUseCase addUseCase;
  private final TransactionUpdateUseCase updateUseCase;
  private final TransactionDeleteUseCase deleteUseCase;
  private final GetTransactionQuery transactionQuery;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final GetDriverQuery driverQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new TransactionAddRequest());
    addTransactionAttributes(model);
    model.addAttribute("drivers", driverQuery.getAll());

    return "forms/addTransaction";
  }

  @PostMapping(value = "/add")
  public String addTransaction(
      @ModelAttribute final TransactionAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);

      return "forms/addTransaction";
    }

    return "redirect:" + TRANSACTION_ROOT_PATH;
  }

  @GetMapping(value = "/add-form/driver/{driverId}")
  public String addFormWithDriver(@PathVariable("driverId") long driverId, final Model model) {
    final var addRequest = new TransactionAddRequest();
    addRequest.setDriverId(driverId);
    model.addAttribute("addRequest", addRequest);
    addTransactionAttributes(model);
    model.addAttribute("driverInfo", driverQuery.getObjectInfo(driverId));
    model.addAttribute("driverId", driverId);

    return "forms/addTransactionWithDriver";
  }

  private void addTransactionAttributes(Model model) {
    model.addAttribute("positiveTransactionTypes", transactionTypeQuery.getPositive());
    model.addAttribute("negativeTransactionTypes", transactionTypeQuery.getNegative());
  }

  @PostMapping(value = "/add/driver")
  public String addTransactionWithDriver(
      @ModelAttribute final TransactionAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);

      return "forms/addTransaction";
    }
    final var driverId = addRequest.getDriverId();

    return "redirect:/balances/driver/" + driverId;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", transactionQuery.getUpdateRequestById(id));
    addTransactionAttributes(model);
    model.addAttribute("drivers", driverQuery.getAll());

    return "forms/updateTransaction";
  }

  @PostMapping("/update")
  public String updateTransactionTransaction(
      final TransactionUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(updateRequest, model);

      return "forms/updateTransaction";
    }

    return "redirect:" + TRANSACTION_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/driver/{id}")
  public String updateFormWithDriver(@PathVariable("id") long id, Model model) {
    final var updateRequest = transactionQuery.getUpdateRequestById(id);
    final var driverId = updateRequest.getDriverId();
    model.addAttribute("updateRequest", updateRequest);
    addTransactionAttributes(model);
    model.addAttribute("driverInfo", driverQuery.getObjectInfo(driverId));
    model.addAttribute("driverId", driverId);

    return "forms/updateTransactionWithDriver";
  }

  @PostMapping("/update/driver")
  public String updateTransactionWithDriver(
      final TransactionUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      addUpdateRequestToModel(updateRequest, model);

      return "forms/updateTransactionWithDriver";
    }
    final var driverId = updateRequest.getDriverId();

    return "redirect:/balances/driver/" + driverId;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") Long id, Model model) {
    model.addAttribute("deleteRequest", new TransactionDeleteRequest(id));
    model.addAttribute("objectInfo", transactionQuery.getObjectInfo(id));

    return "forms/deleteTransaction";
  }

  @PostMapping("/delete")
  public String deleteForm(final TransactionDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/updateTransaction";
    }

    return "redirect:" + TRANSACTION_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/driver/{id}")
  public String deleteFormWithDriver(@PathVariable("id") Long id, Model model) {
    final var driverId = transactionQuery.getUpdateRequestById(id).getDriverId();
    model.addAttribute("deleteRequest", new TransactionDeleteRequest(id));
    model.addAttribute("objectInfo", transactionQuery.getObjectInfo(id));
    model.addAttribute("driverId", driverId);

    return "forms/deleteTransactionWithDriver";
  }

  @PostMapping("/delete/driver/{driverId}")
  public String deleteTransactionWithDriver(
      @PathVariable("driverId") long driverId,
      final TransactionDeleteRequest deleteRequest,
      Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteTransactionWithDriver";
    }

    return "redirect:/balances/driver/" + driverId;
  }

  private void addAddRequestToModel(final TransactionAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }

  private void addUpdateRequestToModel(
      final TransactionUpdateRequest updateRequest, final Model model) {
    model.addAttribute("updateRequest", updateRequest);
  }
}
