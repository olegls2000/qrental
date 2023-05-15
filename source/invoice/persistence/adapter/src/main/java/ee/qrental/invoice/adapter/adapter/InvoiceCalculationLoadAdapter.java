package ee.qrental.invoice.adapter.adapter;

import static ee.qrental.common.core.utils.QTimeUtils.INVOICE_START_CALCULATION_DATE;
import static java.util.stream.Collectors.toList;

import ee.qrental.invoice.adapter.mapper.InvoiceCalculationAdapterMapper;
import ee.qrental.invoice.adapter.repository.InvoiceCalculationRepository;
import ee.qrental.invoice.api.out.InvoiceCalculationLoadPort;
import ee.qrental.invoice.domain.InvoiceCalculation;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceCalculationLoadAdapter implements InvoiceCalculationLoadPort {

  private final InvoiceCalculationRepository repository;
  private final InvoiceCalculationAdapterMapper mapper;

  @Override
  public LocalDate loadLastCalculationDate() {
    return loadedOrDefault(repository.getLastCalculationDate(), INVOICE_START_CALCULATION_DATE);
  }

  private LocalDate loadedOrDefault(final LocalDate loadedDate, final LocalDate defaultDate) {
    return loadedDate == null ? defaultDate : loadedDate;
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
