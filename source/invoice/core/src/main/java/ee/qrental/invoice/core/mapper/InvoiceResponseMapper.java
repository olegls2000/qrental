package ee.qrental.invoice.core.mapper;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.invoice.api.in.response.InvoiceImmutableResponse;
import ee.qrental.invoice.api.in.response.InvoiceResponse;
import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.domain.InvoiceItem;
import java.util.HashMap;

public class InvoiceResponseMapper implements ResponseMapper<InvoiceResponse, Invoice> {
  @Override
  public InvoiceResponse toResponse(final Invoice domain) {
    return InvoiceResponse.builder()
        .id(domain.getId())
        .number(domain.getNumber())
        .weekNumber(domain.getWeekNumber())
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
    return format(
        "Receiver: %s, Sender: %s, Week: %d",
        domain.getDriverCompany(), domain.getQFirmName(), domain.getWeekNumber());
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
