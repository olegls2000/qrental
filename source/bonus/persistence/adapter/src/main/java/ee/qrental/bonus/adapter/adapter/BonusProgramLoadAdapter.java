package ee.qrental.bonus.adapter.adapter;

import ee.qrental.bonus.adapter.mapper.BonusProgramAdapterMapper;
import ee.qrental.bonus.adapter.repository.BonusProgramRepository;
import ee.qrental.bonus.api.out.BonusProgramLoadPort;
import ee.qrental.bonus.domain.BonusProgram;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusProgramLoadAdapter implements BonusProgramLoadPort {

  private final BonusProgramRepository repository;
  private final BonusProgramAdapterMapper mapper;

  @Override
  public List<BonusProgram> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public BonusProgram loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<BonusProgram> loadActive() {
    return repository.findAllByActive(true).stream().map(mapper::mapToDomain).toList();
  }
}
