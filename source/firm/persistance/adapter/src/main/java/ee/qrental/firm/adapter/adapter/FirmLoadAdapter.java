package ee.qrental.firm.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.firm.adapter.mapper.FirmAdapterMapper;
import ee.qrental.firm.adapter.repository.FirmRepository;
import ee.qrental.firm.api.out.FirmLoadPort;
import ee.qrental.firm.domain.Firm;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLoadAdapter implements FirmLoadPort {

  private final FirmRepository repository;
  private final FirmAdapterMapper mapper;

  @Override
  public List<Firm> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Firm loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }
}
