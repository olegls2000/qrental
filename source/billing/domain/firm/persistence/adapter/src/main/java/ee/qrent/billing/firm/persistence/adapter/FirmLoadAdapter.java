package ee.qrent.billing.firm.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.firm.persistence.mapper.FirmAdapterMapper;
import ee.qrent.billing.firm.persistence.repository.FirmRepository;
import ee.qrent.billing.firm.api.out.FirmLoadPort;
import ee.qrent.billing.firm.domain.Firm;
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
