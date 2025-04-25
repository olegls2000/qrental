package ee.qrent.billing.ui.controller.transaction.transaction;

import static ee.qrent.billing.ui.controller.ControllerUtils.TRANSACTION_KIND_ROOT_PATH;

import ee.qrent.billing.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindAddRequest;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindDeleteRequest;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrent.billing.transaction.api.in.usecase.kind.TransactionKindAddUseCase;
import ee.qrent.billing.transaction.api.in.usecase.kind.TransactionKindDeleteUseCase;
import ee.qrent.billing.transaction.api.in.usecase.kind.TransactionKindUpdateUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(TRANSACTION_KIND_ROOT_PATH)
@AllArgsConstructor
public class TransactionKindUseCaseController {

  private final TransactionKindAddUseCase addUseCase;
  private final TransactionKindUpdateUseCase updateUseCase;
  private final TransactionKindDeleteUseCase deleteUseCase;
  private final GetTransactionKindQuery transactionKindQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new TransactionKindAddRequest());

    return "forms/addTransactionKind";
  }

  @PostMapping(value = "/add")
  public String addTransactionTransactionKind(
      @ModelAttribute final TransactionKindAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      model.addAttribute("addRequest", addRequest);

      return "forms/addTransactionKind";
    }

    return "redirect:" + TRANSACTION_KIND_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", transactionKindQuery.getUpdateRequestById(id));

    return "forms/updateTransactionKind";
  }

  @PostMapping("/update")
  public String updateTransactionTransactionKind(
          final TransactionKindUpdateRequest updateRequest, final Model model) {
    updateUseCase.update(updateRequest);
    if (updateRequest.hasViolations()) {
      model.addAttribute("updateRequest", updateRequest);

      return "forms/updateTransactionKind";
    }

    return "redirect:" + TRANSACTION_KIND_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new TransactionKindDeleteRequest(id));
    model.addAttribute("objectInfo", transactionKindQuery.getObjectInfo(id));

    return "forms/deleteTransactionKind";
  }

  @PostMapping("/delete")
  public String deleteForm(final TransactionKindDeleteRequest deleteRequest, final Model model) {
    deleteUseCase.delete(deleteRequest);
    if (deleteRequest.hasViolations()) {
      model.addAttribute("deleteRequest", deleteRequest);

      return "forms/deleteTransactionKind";
    }

    return "redirect:" + TRANSACTION_KIND_ROOT_PATH;
  }
}
