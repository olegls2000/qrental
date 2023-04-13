package ee.qrental.invoice.core.mapper;

import ee.qrental.invoice.api.in.request.InvoiceCalculationRequest;
import ee.qrental.invoice.domain.InvoiceCalculation;

public class InvoiceCalculationRequestMapper {

  public InvoiceCalculation toDomain(final InvoiceCalculationRequest request) {
    final var domain =
        InvoiceCalculation.builder()
            .actionDate(request.getActionDate())
            .comment(request.getComment())
            .build();
    domain.setActionDate(request.getActionDate());

    return domain;
  }
}
