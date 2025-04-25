package ee.qrent.billing.invoice.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.billing.invoice.api.in.request.InvoiceCalculationAddRequest;
import ee.qrent.billing.invoice.domain.InvoiceCalculation;
import java.util.ArrayList;

public class InvoiceCalculationAddRequestMapper
    implements AddRequestMapper<InvoiceCalculationAddRequest, InvoiceCalculation> {

  public InvoiceCalculation toDomain(final InvoiceCalculationAddRequest request) {
    return InvoiceCalculation.builder()
        .actionDate(request.getActionDate())
        .startQWeekId(null) //calculated and set in Service
        .endQWeekId(request.getQWeekId())
        .results(new ArrayList<>())
        .comment(request.getComment())
        .build();
  }
}
