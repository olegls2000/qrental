package ee.qrent.billing.constant.persistence.adapter;

import ee.qrent.billing.constant.persistence.mapper.ConstantAdapterMapper;
import ee.qrent.billing.constant.persistence.repository.ConstantRepository;
import ee.qrent.billing.constant.api.out.ConstantAddPort;
import ee.qrent.billing.constant.api.out.ConstantDeletePort;
import ee.qrent.billing.constant.api.out.ConstantUpdatePort;
import ee.qrent.billing.constant.domain.Constant;
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
