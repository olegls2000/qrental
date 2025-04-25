package ee.qrent.billing.bonus.persistence.adapter;

import ee.qrent.billing.bonus.persistence.mapper.BonusProgramAdapterMapper;
import ee.qrent.billing.bonus.persistence.repository.BonusProgramRepository;
import ee.qrent.billing.bonus.api.out.BonusProgramLoadPort;
import ee.qrent.billing.bonus.domain.BonusProgram;
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
