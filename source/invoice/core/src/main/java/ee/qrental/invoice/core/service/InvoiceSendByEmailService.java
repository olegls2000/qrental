package ee.qrental.invoice.core.service;

import static java.util.Collections.singletonList;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.email.api.in.request.EmailSendRequest;
import ee.qrental.email.api.in.request.EmailType;
import ee.qrental.email.api.in.usecase.EmailSendUseCase;
import ee.qrental.invoice.api.in.request.InvoiceSendByEmailRequest;
import ee.qrental.invoice.api.in.usecase.InvoicePdfUseCase;
import ee.qrental.invoice.api.in.usecase.InvoiceSendByEmailUseCase;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

@AllArgsConstructor
public class InvoiceSendByEmailService implements InvoiceSendByEmailUseCase {

  private final EmailSendUseCase emailSendUseCase;

  private final InvoiceLoadPort invoiceLoadPort;

  private final InvoicePdfUseCase invoicePdfUseCase;

  private final GetDriverQuery driverQuery;

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

    final var emailSendRequest =
        EmailSendRequest.builder()
            .type(EmailType.INVOICE)
            .recipients(singletonList(recipient))
            .attachment(attachment)
            .properties(properties)
            .build();

    emailSendUseCase.sendEmail(emailSendRequest);
  }
}
