package ee.qrental.invoice.core.mapper;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrental.invoice.domain.InvoiceCalculation;
import java.util.ArrayList;

public class InvoiceCalculationAddRequestMapper
    implements AddRequestMapper<InvoiceCalculationAddRequest, InvoiceCalculation> {

  public InvoiceCalculation toDomain(final InvoiceCalculationAddRequest request) {
    return InvoiceCalculation.builder()
        .actionDate(request.getActionDate())
        .results(new ArrayList<>())
        .comment(request.getComment())
        .build();
  }
}
