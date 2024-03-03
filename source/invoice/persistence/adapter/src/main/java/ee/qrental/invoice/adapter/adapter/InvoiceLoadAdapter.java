package ee.qrental.invoice.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.invoice.adapter.mapper.InvoiceAdapterMapper;
import ee.qrental.invoice.adapter.repository.InvoiceRepository;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.invoice.domain.Invoice;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceLoadAdapter implements InvoiceLoadPort {

  private final InvoiceRepository repository;
  private final InvoiceAdapterMapper mapper;

  @Override
  public List<Invoice> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Invoice loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public Invoice loadByWeekAndDriverAndFirm(Long qWekId, Long driverId, Long firmId) {
    return mapper.mapToDomain(
        repository.findByWeekAndDriverIdAndFirmId(qWekId, driverId, firmId));
  }

  @Override
  public Invoice loadByQWeekIdAndDriverId(final Long qWeekId, final Long driverId) {
    return mapper.mapToDomain(
            repository.findByByQWeekIdAndDriverId(qWeekId, driverId));
  }

  @Override
  public List<Invoice> loadAllByCalculationId(Long calculationId) {
    return repository.findByCalculationId(calculationId).stream()
            .map(mapper::mapToDomain)
            .collect(toList());
  }
}
