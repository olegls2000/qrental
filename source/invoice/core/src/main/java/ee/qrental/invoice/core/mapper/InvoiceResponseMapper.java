package ee.qrental.invoice.core.mapper;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.invoice.api.in.response.InvoiceResponse;
import ee.qrental.invoice.domain.Invoice;

public class InvoiceResponseMapper implements ResponseMapper<InvoiceResponse, Invoice> {
  @Override
  public InvoiceResponse toResponse(final Invoice domain) {
    return InvoiceResponse.builder().build();
  }

  @Override
  public String toObjectInfo(Invoice domain) {
    return format("%s %s", domain.getLastName(), domain.getFirstName());
  }
}
