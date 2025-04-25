package ee.qrent.billing.invoice.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.invoice.adapter.mapper.InvoiceCalculationAdapterMapper;
import ee.qrent.billing.invoice.adapter.repository.InvoiceCalculationRepository;
import ee.qrent.billing.invoice.api.out.InvoiceCalculationLoadPort;
import ee.qrent.billing.invoice.domain.InvoiceCalculation;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationLoadAdapter implements InvoiceCalculationLoadPort {

  private final InvoiceCalculationRepository repository;
  private final InvoiceCalculationAdapterMapper mapper;

  @Override
  public InvoiceCalculation loadLastCalculation() {
    final var lastCalculationEntity = repository.getLastCalculation();

    return mapper.mapToDomain(lastCalculationEntity);
  }

  @Override
  public List<InvoiceCalculation> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public InvoiceCalculation loadById(final Long id) {
    final var entity = repository.getReferenceById(id);
    return mapper.mapToDomain(entity);
  }
}
