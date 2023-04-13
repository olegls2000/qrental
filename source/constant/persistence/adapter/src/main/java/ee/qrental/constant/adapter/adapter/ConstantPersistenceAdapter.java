package ee.qrental.constant.adapter.adapter;

import ee.qrental.constant.adapter.mapper.ConstantAdapterMapper;
import ee.qrental.constant.adapter.repository.ConstantRepository;
import ee.qrental.constant.api.out.ConstantAddPort;
import ee.qrental.constant.api.out.ConstantDeletePort;
import ee.qrental.constant.api.out.ConstantUpdatePort;
import ee.qrental.constant.domain.Constant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConstantPersistenceAdapter implements ConstantAddPort, ConstantUpdatePort, ConstantDeletePort {

  private final ConstantRepository repository;
  private final ConstantAdapterMapper mapper;

  @Override
  public Constant add(final Constant domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public Constant update(final Constant domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
