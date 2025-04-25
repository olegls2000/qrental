package ee.qrent.billing.bonus.persistence.adapter;

import ee.qrent.billing.bonus.persistence.mapper.BonusProgramAdapterMapper;
import ee.qrent.billing.bonus.persistence.repository.BonusProgramRepository;
import ee.qrent.billing.bonus.api.out.BonusProgramAddPort;
import ee.qrent.billing.bonus.api.out.BonusProgramDeletePort;
import ee.qrent.billing.bonus.api.out.BonusProgramUpdatePort;
import ee.qrent.billing.bonus.domain.BonusProgram;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusProgramPersistenceAdapter
    implements BonusProgramAddPort, BonusProgramUpdatePort, BonusProgramDeletePort {

  private final BonusProgramRepository repository;
  private final BonusProgramAdapterMapper mapper;

  @Override
  public BonusProgram add(final BonusProgram domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public BonusProgram update(final BonusProgram domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
