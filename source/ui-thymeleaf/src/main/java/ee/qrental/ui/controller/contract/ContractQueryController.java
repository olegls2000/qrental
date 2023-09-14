package ee.qrental.ui.controller.contract;

import static ee.qrental.ui.controller.util.ControllerUtils.CONTRACT_ROOT_PATH;

import ee.qrental.contract.api.in.query.GetContractQuery;
import ee.qrental.contract.api.in.request.ContractSendByEmailRequest;
import ee.qrental.contract.api.in.usecase.ContractPdfUseCase;
import ee.qrental.invoice.api.in.request.InvoiceSendByEmailRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(CONTRACT_ROOT_PATH)
@AllArgsConstructor
public class ContractQueryController {

  private final GetContractQuery contractQuery;
  private final ContractPdfUseCase pdfUseCase;

  @GetMapping
  public String getDriverView(final Model model) {
    model.addAttribute("contracts", contractQuery.getAll());

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
    final var contract = contractQuery.getById(id);
    model.addAttribute("emailSendRequest", ContractSendByEmailRequest.builder().id(id).build());

    return "forms/emailSendInvoice";
  }
}
