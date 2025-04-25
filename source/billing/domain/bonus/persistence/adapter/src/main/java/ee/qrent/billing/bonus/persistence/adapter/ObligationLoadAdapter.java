package ee.qrent.billing.bonus.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.bonus.persistence.mapper.ObligationAdapterMapper;
import ee.qrent.billing.bonus.persistence.repository.ObligationRepository;
import ee.qrent.billing.bonus.api.out.ObligationLoadPort;
import ee.qrent.billing.bonus.domain.Obligation;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationLoadAdapter implements ObligationLoadPort {

  private final ObligationRepository repository;
  private final ObligationAdapterMapper mapper;

  @Override
  public List<Obligation> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Obligation loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<Obligation> loadAllByIds(final List<Long> ids) {
    return repository.findByIds(ids).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public List<Obligation> loadAllByCalculationId(final Long calculationId) {
    return repository.findByCalculationId(calculationId).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }

  @Override
  public Obligation loadByDriverIdAndByQWeekId(final Long driverId, final Long qWeekId) {
    return mapper.mapToDomain(repository.findByDriverIdAndByQWeekId(driverId, qWeekId));
  }
}
