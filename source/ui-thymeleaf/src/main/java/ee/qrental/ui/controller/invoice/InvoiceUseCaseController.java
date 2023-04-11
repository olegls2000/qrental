package ee.qrental.ui.controller.invoice;

import static ee.qrental.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;

import ee.qrental.common.core.utils.QWeek;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.firm.api.in.query.GetFirmQuery;
import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import ee.qrental.invoice.api.in.request.InvoiceAddRequest;
import ee.qrental.invoice.api.in.request.InvoiceDeleteRequest;
import ee.qrental.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrental.invoice.api.in.usecase.InvoiceAddUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceDeleteUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceUpdateUseCase;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(INVOICE_ROOT_PATH)
@AllArgsConstructor
public class InvoiceUseCaseController {

  private final InvoiceAddUseCase addUseCase;
  private final InvoiceUpdateUseCase updateUseCase;
  private final InvoiceDeleteUseCase deleteUseCase;
  private final GetInvoiceQuery invoiceQuery;
  private final GetDriverQuery driverQuery;
  private final GetFirmQuery firmQuery;

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    addAddRequestToModel(new InvoiceAddRequest(), model);
    populateSelectOptions(model);

    return "forms/addInvoice";
  }

  @PostMapping(value = "/add")
  public String addInvoice(@ModelAttribute final InvoiceAddRequest addRequest, final Model model) {
    addUseCase.add(addRequest);
    if (addRequest.hasViolations()) {
      addAddRequestToModel(addRequest, model);
      populateSelectOptions(model);

      return "forms/addInvoice";
    }

    return "redirect:" + INVOICE_ROOT_PATH;
  }

  private void populateSelectOptions(final Model model) {
    model.addAttribute("qFirms", firmQuery.getAll());
    model.addAttribute("drivers", driverQuery.getAll());
    model.addAttribute("years", List.of(2023));
    model.addAttribute("weeks", QWeek.values());
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", invoiceQuery.getUpdateRequestById(id));
    model.addAttribute("immutableData", invoiceQuery.getImmutableDataById(id));

    return "forms/updateInvoice";
  }

  @PostMapping("/update")
  public String updateInvoice(final InvoiceUpdateRequest updateRequest) {
    updateUseCase.update(updateRequest);

    return "redirect:" + INVOICE_ROOT_PATH;
  }

  @GetMapping(value = "/delete-form/{id}")
  public String deleteForm(@PathVariable("id") Long id, Model model) {
    model.addAttribute("deleteRequest", new InvoiceDeleteRequest(id));
    model.addAttribute("objectInfo", invoiceQuery.getObjectInfo(id));

    return "forms/deleteInvoice";
  }

  @PostMapping("/delete")
  public String deleteForm(final InvoiceDeleteRequest deleteRequest) {
    deleteUseCase.delete(deleteRequest);

    return "redirect:" + INVOICE_ROOT_PATH;
  }

  private void addAddRequestToModel(final InvoiceAddRequest addRequest, final Model model) {
    model.addAttribute("addRequest", addRequest);
  }
}
