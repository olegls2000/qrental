package ee.qrental.ui.controller.transaction;

import static ee.qrental.ui.controller.util.ControllerUtils.TRANSACTION_TYPE_ROOT_PATH;

import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.type.TransactionTypeAddRequest;
import ee.qrental.transaction.api.in.request.type.TransactionTypeDeleteRequest;
import ee.qrental.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrental.transaction.api.in.usecase.type.TransactionTypeAddUseCase;
import ee.qrental.transaction.api.in.usecase.type.TransactionTypeDeleteUseCase;
import ee.qrental.transaction.api.in.usecase.type.TransactionTypeUpdateUseCase;
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

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new TransactionTypeAddRequest());

    return "forms/addTransactionType";
  }

  @PostMapping(value = "/add")
  public String addTransactionTransactionType(
      @ModelAttribute final TransactionTypeAddRequest transactionTypeInfo) {
    addUseCase.add(transactionTypeInfo);

    return "redirect:" + TRANSACTION_TYPE_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", transactionTypeQuery.getUpdateRequestById(id));

    return "forms/updateTransactionType";
  }

  @PostMapping("/update")
  public String updateTransactionTransactionType(
      final TransactionTypeUpdateRequest transactionTypeUpdateRequest) {
    updateUseCase.update(transactionTypeUpdateRequest);

    return "redirect:" + TRANSACTION_TYPE_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("deleteRequest", new TransactionTypeDeleteRequest(id));
    model.addAttribute("objectInfo", transactionTypeQuery.getObjectInfo(id));

    return "forms/deleteTransactionType";
  }

  @PostMapping("/delete")
  public String deleteForm(final TransactionTypeDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + TRANSACTION_TYPE_ROOT_PATH;
  }
}
