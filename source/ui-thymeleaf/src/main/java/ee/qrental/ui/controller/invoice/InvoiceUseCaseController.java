package ee.qrental.ui.controller.invoice;

import static ee.qrental.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;

import ee.qrental.common.core.utils.QWeek;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.invoice.api.in.query.GetInvoiceQuery;
import ee.qrental.invoice.api.in.request.InvoiceAddRequest;
import ee.qrental.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrental.invoice.api.in.usecase.InvoiceAddUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceUpdateUseCase;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(INVOICE_ROOT_PATH)
@AllArgsConstructor
public class InvoiceUseCaseController {

  private final InvoiceAddUseCase addUseCase;
  private final InvoiceUpdateUseCase updateUseCase;

  private final GetInvoiceQuery invoiceQuery;

  private final GetDriverQuery driverQuery;

  private final List<QFirm> qFirms = Arrays.asList(new QFirm(3L, "Q3"), new QFirm(4L, "Q4"));

  @GetMapping(value = "/add-form")
  public String addForm(final Model model) {
    model.addAttribute("addRequest", new InvoiceAddRequest());
    model.addAttribute("qFirms", qFirms);
    model.addAttribute("drivers", driverQuery.getAll());
    model.addAttribute("years", List.of(2023));
    model.addAttribute("weeks", QWeek.values());

    return "forms/addInvoice";
  }

  @PostMapping(value = "/add")
  public String addInvoice(@ModelAttribute final InvoiceAddRequest addRequest) {
    addUseCase.add(addRequest);

    return "redirect:" + INVOICE_ROOT_PATH;
  }

  @GetMapping(value = "/update-form/{id}")
  public String updateForm(@PathVariable("id") long id, final Model model) {
    model.addAttribute("updateRequest", invoiceQuery.getUpdateRequestById(id));

    return "forms/updateInvoice";
  }

  @PostMapping("/update")
  public String updateInvoice(final InvoiceUpdateRequest updateRequest) {
    updateUseCase.update(updateRequest);

    return "redirect:" + INVOICE_ROOT_PATH;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class QFirm {
    private Long id;
    private String name;
  }
}
