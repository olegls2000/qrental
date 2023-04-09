package ee.qrental.invoice.adapter.adapter;

import ee.qrental.invoice.adapter.mapper.InvoiceAdapterMapper;
import ee.qrental.invoice.adapter.repository.InvoiceItemRepository;
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
  private final InvoiceItemRepository itemRepository;
  private final InvoiceAdapterMapper mapper;

  @Override
  public Invoice add(final Invoice domain) {
    final var savedInvoiceEntity = repository.save(mapper.mapToEntity(domain));

    domain.getItems().stream()
        .map(item -> mapper.mapToItemEntity(item))
        .peek(entityItem -> entityItem.setInvoice(savedInvoiceEntity))
        .forEach(itemRepository::save);

    return mapper.mapToDomain(savedInvoiceEntity);
  }

  @Override
  public Invoice update(final Invoice domain) {
    repository.save(mapper.mapToEntity(domain));

    return domain;
  }

  @Override
  public void delete(final Long id) {
    itemRepository.deleteByInvoiceId(id);
    repository.deleteById(id);
  }
}
