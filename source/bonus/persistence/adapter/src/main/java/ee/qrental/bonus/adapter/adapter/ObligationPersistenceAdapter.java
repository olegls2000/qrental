package ee.qrental.bonus.adapter.adapter;

import ee.qrental.bonus.adapter.mapper.ObligationAdapterMapper;
import ee.qrental.bonus.adapter.repository.ObligationRepository;
import ee.qrental.bonus.api.out.ObligationAddPort;
import ee.qrental.bonus.domain.Obligation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationPersistenceAdapter implements ObligationAddPort {

  private final ObligationRepository repository;
  private final ObligationAdapterMapper mapper;

  @Override
  public Obligation add(final Obligation domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }
}
