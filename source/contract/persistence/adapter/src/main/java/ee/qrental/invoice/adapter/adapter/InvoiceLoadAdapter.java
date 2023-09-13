package ee.qrental.invoice.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.invoice.adapter.mapper.InvoiceAdapterMapper;
import ee.qrental.invoice.adapter.repository.InvoiceRepository;
import ee.qrental.invoice.api.out.InvoiceLoadPort;
import ee.qrental.contract.domain.Contract;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvoiceLoadAdapter implements InvoiceLoadPort {

  private final InvoiceRepository repository;
  private final InvoiceAdapterMapper mapper;

  @Override
  public List<Contract> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Contract loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public Contract loadByWeekAndDriverAndFirm(Integer weekNumber, Long driverId, Long firmId) {
    return mapper.mapToDomain(
        repository.findByWeekAndDriverIdAndFirmId(weekNumber, driverId, firmId));
  }
}
