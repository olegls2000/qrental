package ee.qrental.invoice.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrental.invoice.api.in.request.InvoiceUpdateRequest;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.domain.Invoice;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceUpdateRequestMapper
    implements UpdateRequestMapper<InvoiceUpdateRequest, Invoice> {

  private final InvoiceLoadPort loadPort;

  @Override
  public Invoice toDomain(final InvoiceUpdateRequest request) {
    final var invoiceFromDb = loadPort.loadById(request.getId());
    invoiceFromDb.setDriverCompany(request.getDriverCompany());
    invoiceFromDb.setDriverCompanyRegNumber(request.getDriverCompanyRegNumber());
    invoiceFromDb.setDriverCompanyAddress(request.getDriverCompanyAddress());
    invoiceFromDb.setQFirmName(request.getQFirmName());
    invoiceFromDb.setQFirmRegNumber(request.getQFirmRegNumber());
    invoiceFromDb.setQFirmVatNumber(request.getQFirmVatNumber());
    invoiceFromDb.setQFirmBank(request.getQFirmIban());
    invoiceFromDb.setComment(request.getComment());

    return invoiceFromDb;
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
