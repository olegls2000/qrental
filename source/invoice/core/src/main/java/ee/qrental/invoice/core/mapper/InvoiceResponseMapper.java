package ee.qrental.invoice.core.mapper;

import static java.lang.String.format;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.invoice.api.in.response.InvoiceResponse;
import ee.qrental.invoice.domain.Invoice;

public class InvoiceResponseMapper implements ResponseMapper<InvoiceResponse, Invoice> {
  @Override
  public InvoiceResponse toResponse(final Invoice domain) {
    return InvoiceResponse.builder()
        .id(domain.getId())
        .number(domain.getNumber())
        .driverCallSign(domain.getDriverCallSign())
        .driverCompany(domain.getDriverCompany())
        .driverCompanyRegNumber(domain.getDriverCompanyRegNumber())
        .driverCompanyAddress(domain.getDriverCompanyAddress())
        .qFirmName(domain.getQFirmName())
        .qFirmRegNumber(domain.getQFirmRegNumber())
        .qFirmVatNumber(domain.getQFirmVatNumber())
        .qFirmBank(domain.getQFirmBank())
        .created(domain.getCreated())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(Invoice domain) {
    return format("Receiver: %s, Sender: %s", domain.getDriverCompany(), domain.getQFirmName());
  }
}
