package ee.qrental.invoice.adapter.adapter;

import ee.qrental.invoice.adapter.mapper.InvoiceAdapterMapper;
import ee.qrental.invoice.adapter.repository.InvoiceRepository;
import ee.qrental.invoice.api.out.InvoiceAddPort;
import ee.qrental.invoice.api.out.InvoiceDeletePort;
import ee.qrental.invoice.api.out.InvoiceUpdatePort;
import ee.qrental.invoice.domain.Invoice;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoicePersistenceAdapter
    implements InvoiceAddPort, InvoiceUpdatePort, InvoiceDeletePort {

  private final InvoiceRepository repository;
  private final InvoiceAdapterMapper mapper;

  @Override
  public Invoice add(final Invoice domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public Invoice update(final Invoice domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
