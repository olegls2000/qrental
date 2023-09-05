package ee.qrental.constant.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.constant.adapter.mapper.ConstantAdapterMapper;
import ee.qrental.constant.adapter.repository.ConstantRepository;
import ee.qrental.constant.api.out.ConstantLoadPort;
import ee.qrental.constant.domain.Constant;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConstantLoadAdapter implements ConstantLoadPort {

  private final ConstantRepository repository;
  private final ConstantAdapterMapper mapper;

  @Override
  public List<Constant> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Constant loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public Constant loadByName(final String name) {
    return mapper.mapToDomain(repository.findByName(name));
  }
}
