package ee.qrent.billing.invoice.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.invoice.api.in.response.InvoiceCalculationResponse;
import ee.qrent.billing.invoice.domain.InvoiceCalculation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationResponseMapper
    implements ResponseMapper<InvoiceCalculationResponse, InvoiceCalculation> {

  private final GetQWeekQuery qWeekQuery;

  @Override
  public InvoiceCalculationResponse toResponse(final InvoiceCalculation domain) {
    if (domain == null) {
      return null;
    }

    final var startQWeek = qWeekQuery.getById(domain.getStartQWeekId());
    final var endQWeek = qWeekQuery.getById(domain.getEndQWeekId());

    return InvoiceCalculationResponse.builder()
        .id(domain.getId())
        .actionDate(domain.getActionDate())
        .startWeek(startQWeek.toString())
        .endWeek(endQWeek.toString())
        .invoicesCount(domain.getResults().size())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final InvoiceCalculation domain) {
    return format("Invoice Calculation was done at: %s", domain.getActionDate());
  }
}
