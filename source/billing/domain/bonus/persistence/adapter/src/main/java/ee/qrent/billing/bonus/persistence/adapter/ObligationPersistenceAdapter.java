package ee.qrent.billing.bonus.persistence.adapter;

import ee.qrent.billing.bonus.persistence.mapper.ObligationAdapterMapper;
import ee.qrent.billing.bonus.persistence.repository.ObligationRepository;
import ee.qrent.billing.bonus.api.out.ObligationAddPort;
import ee.qrent.billing.bonus.domain.Obligation;
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
