package ee.qrent.billing.driver.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.driver.persistence.mapper.CallSignAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.CallSignRepository;
import ee.qrent.billing.driver.api.out.CallSignLoadPort;
import ee.qrent.billing.driver.domain.CallSign;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLoadAdapter implements CallSignLoadPort {

  private final CallSignRepository repository;
  private final CallSignAdapterMapper mapper;

  @Override
  public List<CallSign> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public List<CallSign> loadAvailable() {
    final var nowDate = LocalDate.now();

    return repository.findAvailable(nowDate).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public CallSign loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public CallSign loadByCallSign(final Integer callSign) {
    return mapper.mapToDomain(repository.findByCallSign(callSign));
  }
}
