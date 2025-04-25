package ee.qrent.billing.ui.controller.driver;

import static ee.qrent.billing.ui.controller.ControllerUtils.AUTHORIZATION_ROOT_PATH;

import ee.qrent.billing.contract.api.in.query.GetAuthorizationQuery;
import ee.qrent.billing.contract.api.in.request.AuthorizationSendByEmailRequest;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationPdfUseCase;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationSendByEmailUseCase;
import ee.qrent.billing.ui.formatter.QDateFormatter;
import ee.qrent.billing.ui.service.driver.DriverCounterService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(AUTHORIZATION_ROOT_PATH)
public class AuthorizationQueryController extends AbstractDriverQueryController {

  private final GetAuthorizationQuery authorizationBoltQuery;
  private final AuthorizationSendByEmailUseCase authorizationBoltSendByEmailUseCase;
  private final AuthorizationPdfUseCase pdfUseCase;

  public AuthorizationQueryController(
      final DriverCounterService driverCounterService,
      final QDateFormatter qDateFormatter,
      final GetAuthorizationQuery authorizationBoltQuery,
      final AuthorizationSendByEmailUseCase authorizationBoltSendByEmailUseCase,
      final AuthorizationPdfUseCase pdfUseCase) {
    super(driverCounterService, qDateFormatter);
    this.authorizationBoltQuery = authorizationBoltQuery;
    this.authorizationBoltSendByEmailUseCase = authorizationBoltSendByEmailUseCase;
    this.pdfUseCase = pdfUseCase;
  }

  @GetMapping
  public String getAuthorizationsView(final Model model) {
    model.addAttribute("authorizations", authorizationBoltQuery.getAll());
    addDriverCounts(model);
    addDateFormatter(model);

    return "authorizations";
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
    final var emailSendRequest = new AuthorizationSendByEmailRequest();
    emailSendRequest.setId(id);
    model.addAttribute("emailSendRequest", emailSendRequest);

    return "forms/emailSendAuthorization";
  }

  @PostMapping("/email/send")
  public String sendByEmail(final AuthorizationSendByEmailRequest emailSendRequest) {
    authorizationBoltSendByEmailUseCase.sendByEmail(emailSendRequest);

    return "redirect:" + AUTHORIZATION_ROOT_PATH;
  }
}
