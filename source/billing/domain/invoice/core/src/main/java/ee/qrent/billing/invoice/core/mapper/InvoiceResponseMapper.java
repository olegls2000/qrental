package ee.qrent.billing.invoice.core.mapper;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.invoice.api.in.response.InvoiceImmutableResponse;
import ee.qrent.billing.invoice.api.in.response.InvoiceResponse;
import ee.qrent.billing.invoice.domain.Invoice;
import ee.qrent.billing.invoice.domain.InvoiceItem;
import java.util.HashMap;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceResponseMapper implements ResponseMapper<InvoiceResponse, Invoice> {

  private final GetQWeekQuery qWeekQuery;

  @Override
  public InvoiceResponse toResponse(final Invoice domain) {
    final var qWeek = qWeekQuery.getById(domain.getQWeekId());
    return InvoiceResponse.builder()
        .id(domain.getId())
        .number(domain.getNumber())
        .weekNumber(qWeek.getNumber())
        .year(qWeek.getYear())
        .driverCompany(domain.getDriverCompany())
        .driverCompanyRegNumber(domain.getDriverCompanyRegNumber())
        .driverCompanyAddress(domain.getDriverCompanyAddress())
        .qFirmName(domain.getQFirmName())
        .qFirmRegNumber(domain.getQFirmRegNumber())
        .qFirmVatNumber(domain.getQFirmVatNumber())
        .qFirmIban(domain.getQFirmIban())
        .qFirmBank(domain.getQFirmBank())
        .created(domain.getCreated())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final Invoice domain) {

    final var qWeek = qWeekQuery.getById(domain.getQWeekId());
    return format(
        "Receiver: %s, Sender: %s, Year: %d, Week: %d",
        domain.getDriverCompany(), domain.getQFirmName(), qWeek.getYear(), qWeek.getNumber());
  }

  public InvoiceImmutableResponse toImmutableData(final Invoice domain) {
    return InvoiceImmutableResponse.builder()
        .number(domain.getNumber())
        .created(domain.getCreated())
        .items(
            domain.getItems().stream()
                .collect(
                    toMap(InvoiceItem::getType, InvoiceItem::getAmount, (a, b) -> b, HashMap::new)))
        .build();
  }
}
