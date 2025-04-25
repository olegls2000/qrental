package ee.qrent.billing.invoice.api.in.usecase;

import ee.qrent.billing.invoice.api.in.request.InvoiceSendByEmailRequest;

public interface InvoiceSendByEmailUseCase {

  void sendByEmail(final InvoiceSendByEmailRequest request);
}
