package ee.qrent.billing.ui.controller.report;

import static ee.qrent.billing.ui.controller.ControllerUtils.REPORT_ROOT_PATH;

import ee.qrent.billing.report.api.in.query.GetWeeklyReportQuery;
import ee.qrent.billing.report.api.in.request.WeeklyReportSendByEmailRequest;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportPdfUseCase;
import ee.qrent.billing.report.api.in.usecase.WeeklyReportSendByEmailUseCase;
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
  private final WeeklyReportSendByEmailUseCase weeklyReportSendByEmailUseCase;
  private final WeeklyReportPdfUseCase reportPdfUseCase;

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
        .body(new InputStreamResource(reportPdfUseCase.getPdfInputStreamById(id)));
  }

  @GetMapping(value = "/email/send-form/{id}")
  public String addForm(@PathVariable("id") long id, final Model model) {
    final var emailSendRequest = new WeeklyReportSendByEmailRequest();
    emailSendRequest.setId(id);
    model.addAttribute("emailSendRequest", emailSendRequest);

    return "forms/emailSendWeeklyReport";
  }

  @PostMapping("/email/send")
  public String sendByEmail(final WeeklyReportSendByEmailRequest emailSendRequest) {
    weeklyReportSendByEmailUseCase.sendByEmail(emailSendRequest);

    return "redirect:" + REPORT_ROOT_PATH;
  }
}
