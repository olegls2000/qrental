package ee.qrental.contract.core.service;

import static java.util.Collections.singletonList;

import ee.qrental.contract.api.in.request.ContractSendByEmailRequest;
import ee.qrental.contract.api.in.usecase.ContractPdfUseCase;
import ee.qrental.contract.api.in.usecase.ContractSendByEmailUseCase;
import ee.qrental.contract.api.out.ContractLoadPort;
import ee.qrental.email.api.in.request.EmailSendRequest;
import ee.qrental.email.api.in.request.EmailType;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class ContractSendByEmailService implements ContractSendByEmailUseCase {

  private final EmailSendUseCase emailSendUseCase;

  private final ContractLoadPort contractLoadPort;

  private final ContractPdfUseCase contractPdfUseCase;

  @SneakyThrows
  @Override
  public void sendByEmail(ContractSendByEmailRequest request) {
    final var contractId = request.getId();
    final var contract = contractLoadPort.loadById(contractId);
 
    final var recipient = contract.getRenterEmail();
    final var attachment = contractPdfUseCase.getPdfInputStreamById(contractId);
    final var properties = new HashMap<String, Object>();
    properties.put("contractNumber", contract.getNumber());

    final var emailSendRequest =
        EmailSendRequest.builder()
            .type(EmailType.CONTRACT)
            .recipients(singletonList(recipient))
            .attachment(attachment)
            .properties(properties)
            .build();

    emailSendUseCase.sendEmail(emailSendRequest);
  }
}
