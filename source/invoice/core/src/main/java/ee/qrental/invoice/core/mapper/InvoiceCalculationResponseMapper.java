package ee.qrental.invoice.core.mapper;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.invoice.api.in.response.InvoiceCalculationResponse;
import ee.qrental.invoice.domain.InvoiceCalculation;

public class InvoiceCalculationResponseMapper
    implements ResponseMapper<InvoiceCalculationResponse, InvoiceCalculation> {
  @Override
  public InvoiceCalculationResponse toResponse(final InvoiceCalculation domain) {
    if (domain == null) {
      return null;
    }
    return InvoiceCalculationResponse.builder()
        .id(domain.getId())
        .actionDate(domain.getActionDate())
        .invoicesCount(domain.getResults().size())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final InvoiceCalculation domain) {
    return format("Invoice Calculation was done at: %s", domain.getActionDate());
  }
}
