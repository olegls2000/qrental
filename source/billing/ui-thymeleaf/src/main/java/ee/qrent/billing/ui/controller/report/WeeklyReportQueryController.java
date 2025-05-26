package ee.qrent.billing.ui.controller.report;

import static ee.qrent.billing.ui.controller.ControllerUtils.INVOICE_ROOT_PATH;
import static ee.qrent.billing.ui.controller.ControllerUtils.REPORT_ROOT_PATH;

import ee.qrent.billing.invoice.api.in.request.InvoiceSendByEmailRequest;
import ee.qrent.billing.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrent.billing.invoice.api.in.usecase.InvoiceSendByEmailUseCase;
import java.io.IOException;

import ee.qrent.billing.report.api.in.query.GetWeeklyReportQuery;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(REPORT_ROOT_PATH)
@AllArgsConstructor
public class WeeklyReportQueryController {

  private final GetWeeklyReportQuery weeklyReportQuery;
  private final InvoiceSendByEmailUseCase invoiceSendByEmailUseCase;
  private final InvoicePdfUseCase invoicePdfUseCase;

  @GetMapping("/weekly-reports")
  public String getTableView(final Model model) {
    model.addAttribute("reports", weeklyReportQuery.getAll());

    return "weeklyReports";
  }

  @GetMapping("/pdf/{id}")
  @ResponseBody
  public ResponseEntity<InputStreamResource> getPdf(@PathVariable("id") long id) {

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(invoicePdfUseCase.getPdfInputStreamById(id)));
  }

  @GetMapping(value = "/email/send-form/{id}")
  public String addForm(@PathVariable("id") long id, final Model model) {
    final var emailSendRequest = new InvoiceSendByEmailRequest();
    emailSendRequest.setId(id);
    model.addAttribute("emailSendRequest", emailSendRequest);

    return "forms/emailSendInvoice";
  }

  @PostMapping("/email/send")
  public String sendByEmail(final InvoiceSendByEmailRequest emailSendRequest) throws IOException {
    invoiceSendByEmailUseCase.sendByEmail(emailSendRequest);

    return "redirect:" + INVOICE_ROOT_PATH;
  }
}
