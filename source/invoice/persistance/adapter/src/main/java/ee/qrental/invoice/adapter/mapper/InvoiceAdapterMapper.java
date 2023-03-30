package ee.qrental.invoice.adapter.mapper;

import ee.qrental.invoice.domain.Invoice;
import ee.qrental.invoice.entity.jakarta.InvoiceJakartaEntity;

public class InvoiceAdapterMapper {

  public Invoice mapToDomain(final InvoiceJakartaEntity entity) {
    return Invoice.builder().build();
  }

  public InvoiceJakartaEntity mapToEntity(final Invoice domain) {
    return InvoiceJakartaEntity.builder().build();
  }
}
