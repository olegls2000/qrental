package ee.qrental.bonus.adapter.adapter;

import ee.qrental.bonus.adapter.mapper.BonusProgramAdapterMapper;
import ee.qrental.bonus.adapter.repository.BonusProgramRepository;
import ee.qrental.bonus.api.out.BonusProgramAddPort;
import ee.qrental.bonus.api.out.BonusProgramDeletePort;
import ee.qrental.bonus.api.out.BonusProgramUpdatePort;
import ee.qrental.bonus.domain.BonusProgram;
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
