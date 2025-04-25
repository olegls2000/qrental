package ee.qrent.billing.invoice.core.service;

import static ee.qrent.queue.api.in.EntryType.INVOICE_EMAIL;
import static java.util.Collections.singletonList;

import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.invoice.api.in.request.InvoiceSendByEmailRequest;
import ee.qrent.billing.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrent.billing.invoice.api.in.usecase.InvoiceSendByEmailUseCase;
import ee.qrent.billing.invoice.api.out.InvoiceLoadPort;
import java.util.HashMap;

import ee.qrent.queue.api.in.QueueEntryPushRequest;
import ee.qrent.queue.api.in.QueueEntryPushUseCase;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class InvoiceSendByEmailService implements InvoiceSendByEmailUseCase {

  private final QueueEntryPushUseCase notificationQueuePushUseCase;
  private final InvoiceLoadPort invoiceLoadPort;
  private final InvoicePdfUseCase invoicePdfUseCase;
  private final GetDriverQuery driverQuery;
  private final QDateTime qDateTime;

  @SneakyThrows
  @Override
  public void sendByEmail(InvoiceSendByEmailRequest request) {
    final var invoiceId = request.getId();
    final var invoice = invoiceLoadPort.loadById(invoiceId);
    final var driverId = invoice.getDriverId();
    final var driver = driverQuery.getById(driverId);
    final var recipient = driver.getEmail();
    final var attachment = invoicePdfUseCase.getPdfInputStreamById(invoiceId);
    final var properties = new HashMap<String, Object>();
    properties.put("invoiceNumber", invoice.getNumber());
    final var notificationQueuePushRequest =
        QueueEntryPushRequest.builder()
            .occurredAt(qDateTime.getNow())
            .type(INVOICE_EMAIL)
            .payloadRecipients(singletonList(recipient))
            .payloadAttachment(attachment)
            .payloadProperties(properties)
            .build();

    notificationQueuePushUseCase.push(notificationQueuePushRequest);
  }
}
