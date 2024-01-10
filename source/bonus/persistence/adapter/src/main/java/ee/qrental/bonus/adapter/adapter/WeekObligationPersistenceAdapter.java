package ee.qrental.bonus.adapter.adapter;

import ee.qrental.bonus.adapter.mapper.WeekObligationAdapterMapper;
import ee.qrental.bonus.adapter.repository.WeekObligationRepository;
import ee.qrental.bonus.api.out.WeekObligationAddPort;
import ee.qrental.bonus.api.out.WeekObligationDeletePort;
import ee.qrental.bonus.domain.WeekObligation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeekObligationPersistenceAdapter implements WeekObligationAddPort, WeekObligationDeletePort {

  private final WeekObligationRepository repository;
  private final WeekObligationAdapterMapper mapper;

  @Override
  public WeekObligation add(final WeekObligation domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
