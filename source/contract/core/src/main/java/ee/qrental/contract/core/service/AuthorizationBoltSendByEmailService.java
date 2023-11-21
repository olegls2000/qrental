package ee.qrental.contract.core.service;

import static java.util.Collections.singletonList;

import ee.qrental.contract.api.in.request.AuthorizationBoltSendByEmailRequest;
import ee.qrental.contract.api.in.usecase.AuthorizationBoltPdfUseCase;
import ee.qrental.contract.api.in.usecase.AuthorizationBoltSendByEmailUseCase;
import ee.qrental.contract.api.out.AuthorizationBoltLoadPort;
import ee.qrental.email.api.in.request.EmailSendRequest;
import ee.qrental.email.api.in.request.EmailType;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class AuthorizationBoltSendByEmailService implements AuthorizationBoltSendByEmailUseCase {

  private final EmailSendUseCase emailSendUseCase;
  private final AuthorizationBoltLoadPort loadPort;
  private final AuthorizationBoltPdfUseCase pdfUseCase;

  @SneakyThrows
  @Override
  public void sendByEmail(final AuthorizationBoltSendByEmailRequest request) {
    final var authorizationId = request.getId();
    final var authorization = loadPort.loadById(authorizationId);

    final var recipient = authorization.getDriverEmail();
    final var attachment = pdfUseCase.getPdfInputStreamById(authorizationId);
    final var properties = new HashMap<String, Object>();

    final var emailSendRequest =
        EmailSendRequest.builder()
            .type(EmailType.AUTHORIZATION_BOLT)
            .recipients(singletonList(recipient))
            .attachment(attachment)
            .properties(properties)
            .build();

    emailSendUseCase.sendEmail(emailSendRequest);
  }
}
