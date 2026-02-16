package ee.qrent.billing.driver.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.driver.persistence.mapper.DriverAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.DriverRepository;
import ee.qrent.billing.driver.api.out.DriverLoadPort;
import ee.qrent.billing.driver.domain.Driver;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverLoadAdapter implements DriverLoadPort {

  private final DriverRepository repository;
  private final DriverAdapterMapper mapper;

  @Override
  public List<Driver> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Driver loadById(final Long id) {
    return mapper.mapToDomain(repository.findById(id));
  }

  @Override
  public List<Driver> loadByMatchCountAndQWeekId(final Integer matchCount, final Long qWeekId) {
    return repository.findAllByMatchCountAndQWeekId(matchCount, qWeekId).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }

  @Override
  public Driver loadByTaxNumber(final Long taxNumber) {
    return mapper.mapToDomain(repository.findByTaxNumber(taxNumber));
  }

  @Override
  public Driver loadByBoltId(final String boltId) {
    return mapper.mapToDomain(repository.findByBoltId(boltId));
  }

  @Override
  public Long loadCountAll() {
    return repository.countAll();
  }

  @Override
  public Long loadCountByActive(final boolean active) {
    return repository.countByActive(active);
  }

  @Override
  public List<Driver> loadByActive(final boolean active) {
    return repository.findByActive(active).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }
}
