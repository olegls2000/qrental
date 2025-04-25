package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.CONTRACT_ROOT_PATH;

import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.request.ContractSendByEmailRequest;
import ee.qrent.billing.contract.api.in.usecase.ContractPdfUseCase;
import ee.qrent.billing.contract.api.in.usecase.ContractSendByEmailUseCase;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import ee.qrent.billing.ui.service.driver.DriverCounterService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CONTRACT_ROOT_PATH)
public class ContractQueryController extends AbstractDriverQueryController {

  private final GetContractQuery contractQuery;
  private final ContractSendByEmailUseCase contractSendByEmailUseCase;
  private final ContractPdfUseCase pdfUseCase;

  public ContractQueryController(
      final DriverCounterService driverCounterService,
      final QDateFormatter qDateFormatter,
      final GetContractQuery contractQuery,
      final ContractSendByEmailUseCase contractSendByEmailUseCase,
      final ContractPdfUseCase pdfUseCase) {
    super(driverCounterService, qDateFormatter);
    this.contractQuery = contractQuery;
    this.contractSendByEmailUseCase = contractSendByEmailUseCase;
    this.pdfUseCase = pdfUseCase;
  }

  @GetMapping(value = "/active")
  public String getActiveContractView(final Model model) {
    model.addAttribute("contractsActive", contractQuery.getAllActiveForCurrentDate());
    addDriverCounts(model);
    addDateFormatter(model);

    return "contractsActive";
  }

  @GetMapping(value = "/closed")
  public String getClosedContractView(final Model model) {
    model.addAttribute("contractsClosed", contractQuery.getClosed());
    addDriverCounts(model);
    addDateFormatter(model);

    return "contractsClosed";
  }

  @GetMapping("/pdf/{id}")
  @ResponseBody
  public ResponseEntity<InputStreamResource> getPdf(@PathVariable("id") long id) {

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(pdfUseCase.getPdfInputStreamById(id)));
  }

  @GetMapping(value = "/email/send-form/{id}")
  public String addForm(@PathVariable("id") long id, final Model model) {
    final var emailSendRequest = new ContractSendByEmailRequest();
    emailSendRequest.setId(id);
    model.addAttribute("emailSendRequest", emailSendRequest);

    return "forms/emailSendContract";
  }

  @PostMapping("/email/send")
  public String sendByEmail(final ContractSendByEmailRequest emailSendRequest) {
    contractSendByEmailUseCase.sendByEmail(emailSendRequest);

    return "redirect:" + CONTRACT_ROOT_PATH;
  }
}
