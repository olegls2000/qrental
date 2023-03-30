package ee.qrental.invoice.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrental.invoice.domain.Invoice;

public class InvoiceUpdateRequestMapper
    implements UpdateRequestMapper<InvoiceUpdateRequest, Invoice> {

  @Override
  public Invoice toDomain(final InvoiceUpdateRequest request) {
    return Invoice.builder().comment(request.getComment()).build();
  }

  @Override
  public InvoiceUpdateRequest toRequest(final Invoice domain) {
    return InvoiceUpdateRequest.builder().comment(domain.getComment()).build();
  }
}
