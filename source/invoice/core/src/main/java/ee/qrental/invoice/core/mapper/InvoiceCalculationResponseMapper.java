package ee.qrental.invoice.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.invoice.api.in.response.InvoiceCalculationResponse;
import ee.qrental.invoice.domain.InvoiceCalculation;
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
