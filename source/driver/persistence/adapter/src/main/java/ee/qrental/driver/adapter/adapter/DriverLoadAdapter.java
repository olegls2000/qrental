package ee.qrental.driver.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.driver.adapter.mapper.DriverAdapterMapper;
import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.adapter.repository.CallSignRepository;
import ee.qrental.driver.adapter.repository.DriverRepository;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.domain.Driver;
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
  public Driver loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public Driver loadByIsukuKood(final Long isukuKood) {
    return mapper.mapToDomain(repository.getByIsikukood(isukuKood));
  }
}
