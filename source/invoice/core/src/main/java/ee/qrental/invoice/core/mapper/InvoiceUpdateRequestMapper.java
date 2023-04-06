package ee.qrental.invoice.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrental.invoice.domain.Invoice;

public class InvoiceUpdateRequestMapper
    implements UpdateRequestMapper<InvoiceUpdateRequest, Invoice> {

  @Override
  public Invoice toDomain(final InvoiceUpdateRequest request) {
    return Invoice.builder()
        .id(request.getId())
        .driverCompany(request.getDriverCompany())
        .driverCompanyRegNumber(request.getDriverCompanyRegNumber())
        .driverCompanyAddress(request.getDriverCompanyAddress())
        .qFirmName(request.getQFirmName())
        .qFirmRegNumber(request.getQFirmRegNumber())
        .qFirmVatNumber(request.getQFirmVatNumber())
        .qFirmBank(request.getQFirmIban())
        .comment(request.getComment())
        .build();
  }

  @Override
  public InvoiceUpdateRequest toRequest(final Invoice domain) {
    return InvoiceUpdateRequest.builder()
        .id(domain.getId())
        .driverCompany(domain.getDriverCompany())
        .driverCompanyRegNumber(domain.getDriverCompanyRegNumber())
        .driverCompanyAddress(domain.getDriverCompanyAddress())
        .qFirmName(domain.getQFirmName())
        .qFirmRegNumber(domain.getQFirmRegNumber())
        .qFirmVatNumber(domain.getQFirmVatNumber())
        .qFirmBank(domain.getQFirmBank())
        .qFirmIban(domain.getQFirmIban())
        .comment(domain.getComment())
        .build();
  }
}
