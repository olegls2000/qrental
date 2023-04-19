package ee.qrental.invoice.api.in.usecase;

import ee.qrental.invoice.api.in.request.InvoiceSendByEmailRequest;

public interface InvoiceSendByEmailUseCase {

  void sendByEmail(final InvoiceSendByEmailRequest request);
}
