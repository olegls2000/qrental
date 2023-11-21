package ee.qrental.ui.controller.contract;

import static ee.qrental.ui.controller.formatter.QDateFormatter.MODEL_ATTRIBUTE_DATE_FORMATTER;
import static ee.qrental.ui.controller.util.ControllerUtils.AUTHORIZATION_ROOT_PATH;

import ee.qrental.contract.api.in.query.GetAuthorizationQuery;
import ee.qrental.contract.api.in.request.AuthorizationSendByEmailRequest;
import ee.qrental.contract.api.in.usecase.AuthorizationPdfUseCase;
import ee.qrental.contract.api.in.usecase.AuthorizationSendByEmailUseCase;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.ui.controller.formatter.QDateFormatter;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(AUTHORIZATION_ROOT_PATH)
@AllArgsConstructor
public class AuthorizationQueryController {
  private final QDateFormatter qDateFormatter;
  private final GetAuthorizationQuery authorizationBoltQuery;
  private final AuthorizationSendByEmailUseCase authorizationBoltSendByEmailUseCase;
  private final AuthorizationPdfUseCase pdfUseCase;
  private final GetCallSignLinkQuery callSignLinkQuery;

  @GetMapping
  public String getAuthorizationsView(final Model model) {
    model.addAttribute(MODEL_ATTRIBUTE_DATE_FORMATTER, qDateFormatter);

    model.addAttribute("authorizations", authorizationBoltQuery.getAll());
    populateLinksCounts(model);

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

  private void populateLinksCounts(final Model model) {
    final var activeLinksCount = callSignLinkQuery.getCountActive();
    model.addAttribute("activeLinksCount", activeLinksCount);
    final var closedLinksCount = callSignLinkQuery.getCountClosed();
    model.addAttribute("closedLinksCount", closedLinksCount);
  }
}
