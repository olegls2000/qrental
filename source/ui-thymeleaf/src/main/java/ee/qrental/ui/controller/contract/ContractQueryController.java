package ee.qrental.ui.controller.contract;

import static ee.qrental.ui.controller.util.ControllerUtils.CONTRACT_ROOT_PATH;

import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.contract.api.in.request.ContractSendByEmailRequest;
import ee.qrental.contract.api.in.usecase.ContractPdfUseCase;
import ee.qrental.contract.api.in.usecase.ContractSendByEmailUseCase;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(CONTRACT_ROOT_PATH)
@AllArgsConstructor
public class ContractQueryController {

  private final GetContractQuery contractQuery;
  private final ContractSendByEmailUseCase contractSendByEmailUseCase;
  private final ContractPdfUseCase pdfUseCase;
  private final GetCallSignLinkQuery callSignLinkQuery;

  @GetMapping
  public String getDriverView(final Model model) {
    model.addAttribute("contracts", contractQuery.getAll());
    populateLinksCounts(model);

    return "contracts";
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
  public String sendByEmail(final ContractSendByEmailRequest emailSendRequest){
    contractSendByEmailUseCase.sendByEmail(emailSendRequest);

    return "redirect:" + CONTRACT_ROOT_PATH;
  }

  private void populateLinksCounts(final Model model) {
    final var activeLinksCount = callSignLinkQuery.getCountActive();
    model.addAttribute("activeLinksCount", activeLinksCount);
    final var closedLinksCount = callSignLinkQuery.getCountClosed();
    model.addAttribute("closedLinksCount", closedLinksCount);
  }
}
