package ee.qrent.billing.contract.core.service;

import static ee.qrent.queue.api.in.EntryType.CONTRACT_EMAIL;
import static java.util.Collections.singletonList;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import ee.qrent.billing.contract.api.in.request.ContractSendByEmailRequest;
import ee.qrent.billing.contract.api.in.usecase.ContractPdfUseCase;
import ee.qrent.billing.contract.api.in.usecase.ContractSendByEmailUseCase;
import ee.qrent.billing.contract.api.out.ContractLoadPort;

import java.util.HashMap;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class ContractSendByEmailService implements ContractSendByEmailUseCase {

  private final ContractLoadPort contractLoadPort;
  private final ContractPdfUseCase contractPdfUseCase;
  private final QueueEntryPushUseCase notificationQueuePushUseCase;
  private final QDateTime qDateTime;

  @Transactional
  @SneakyThrows
  @Override
  public void sendByEmail(final ContractSendByEmailRequest request) {
    final var contractId = request.getId();
    final var contract = contractLoadPort.loadById(contractId);
    final var recipient = contract.getRenterEmail();
    final var attachment = contractPdfUseCase.getPdfInputStreamById(contractId);
    final var properties = new HashMap<String, Object>();
    properties.put("contractNumber", contract.getNumber());
    final var notificationQueuePushRequest =
        QueueEntryPushRequest.builder()
            .occurredAt(qDateTime.getNow())
            .type(CONTRACT_EMAIL)
            .payloadRecipients(singletonList(recipient))
            .payloadAttachment(attachment)
            .payloadProperties(properties)
            .build();

    notificationQueuePushUseCase.push(notificationQueuePushRequest);
  }
}
