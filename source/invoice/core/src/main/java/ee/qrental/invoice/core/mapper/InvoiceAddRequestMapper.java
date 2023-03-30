package ee.qrental.invoice.core.mapper;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.invoice.api.in.request.InvoiceAddRequest;
import ee.qrental.invoice.domain.Invoice;

public class InvoiceAddRequestMapper implements AddRequestMapper<InvoiceAddRequest, Invoice> {

  @Override
  public Invoice toDomain(InvoiceAddRequest request) {
    return Invoice.builder().build();
  }
}
