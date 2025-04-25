package ee.qrent.billing.contract.core.service;

import static java.util.Collections.singletonList;

import ee.qrent.billing.contract.api.in.request.AuthorizationSendByEmailRequest;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationPdfUseCase;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationSendByEmailUseCase;
import ee.qrent.billing.contract.api.out.AuthorizationLoadPort;
import java.util.HashMap;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.EntryType;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class AuthorizationSendByEmailService implements AuthorizationSendByEmailUseCase {

  private final QueueEntryPushUseCase notificationQueuePushUseCase;
  private final AuthorizationLoadPort loadPort;
  private final AuthorizationPdfUseCase pdfUseCase;
  private final QDateTime qDateTime;

  @SneakyThrows
  @Override
  public void sendByEmail(final AuthorizationSendByEmailRequest request) {
    final var authorizationId = request.getId();
    final var authorization = loadPort.loadById(authorizationId);

    final var recipient = authorization.getDriverEmail();
    final var attachment = pdfUseCase.getPdfInputStreamById(authorizationId);
    final var properties = new HashMap<String, Object>();
    properties.put("isikukood", authorization.getDriverIsikukood());

    final var notificationQueuePushRequest =
        QueueEntryPushRequest.builder()
            .occurredAt(qDateTime.getNow())
            .type(EntryType.AUTHORIZATION_EMAIL)
            .payloadRecipients(singletonList(recipient))
            .payloadAttachment(attachment)
            .payloadProperties(properties)
            .build();

    notificationQueuePushUseCase.push(notificationQueuePushRequest);
  }
}
