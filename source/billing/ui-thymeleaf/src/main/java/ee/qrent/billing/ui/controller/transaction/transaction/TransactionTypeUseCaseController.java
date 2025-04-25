package ee.qrent.billing.ui.controller.transaction.transaction;

import static ee.qrent.billing.ui.controller.ControllerUtils.TRANSACTION_TYPE_ROOT_PATH;

import ee.qrent.billing.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrent.billing.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeAddRequest;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeDeleteRequest;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrent.billing.transaction.api.in.usecase.type.TransactionTypeAddUseCase;
import ee.qrent.billing.transaction.api.in.usecase.type.TransactionTypeDeleteUseCase;
import ee.qrent.billing.transaction.api.in.usecase.type.TransactionTypeUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(TRANSACTION_TYPE_ROOT_PATH)
@AllArgsConstructor
public class TransactionTypeUseCaseController {

  private final TransactionTypeAddUseCase addUseCase;
  private final TransactionTypeUpdateUseCase updateUseCase;
  private final TransactionTypeDeleteUseCase deleteUseCase;
  private final GetTransactionTypeQuery transactionTypeQuery;
  private final GetTransactionKindQuery transactionKindQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new TransactionTypeAddRequest());
    model.addAttribute("transactionKinds", transactionKindQuery.getAll());
    return "forms/addTransactionType";
  }

  @PostMapping(value = "/add")
  public String addTransactionTransactionType(
      @ModelAttribute final TransactionTypeAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);
      model.addAttribute("transactionKinds", transactionKindQuery.getAll());

      return "forms/addTransactionType";
    }

    return "redirect:" + TRANSACTION_TYPE_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", transactionTypeQuery.getUpdateRequestById(id));
    model.addAttribute("transactionKinds", transactionKindQuery.getAll());
    return "forms/updateTransactionType";
  }

  @PostMapping("/update")
  public String updateTransactionTransactionType(
      final TransactionTypeUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);
      model.addAttribute("transactionKinds", transactionKindQuery.getAll());
      return "forms/updateTransactionType";
    }

    return "redirect:" + TRANSACTION_TYPE_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new TransactionTypeDeleteRequest(id));
    model.addAttribute("objectInfo", transactionTypeQuery.getObjectInfo(id));

    return "forms/deleteTransactionType";
  }

  @PostMapping("/delete")
  public String deleteForm(final TransactionTypeDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteTransactionType";
    }

    return "redirect:" + TRANSACTION_TYPE_ROOT_PATH;
  }
}
