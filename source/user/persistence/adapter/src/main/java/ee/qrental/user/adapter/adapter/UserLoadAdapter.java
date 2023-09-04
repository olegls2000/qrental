package ee.qrental.user.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.user.adapter.mapper.UserAdapterMapper;
import ee.qrental.user.adapter.repository.UserRepository;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.domain.Driver;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserLoadAdapter implements DriverLoadPort {

  private final UserRepository repository;
  private final UserAdapterMapper mapper;

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
