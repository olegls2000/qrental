package ee.qrent.billing.constant.persistence.adapter;

import ee.qrent.billing.constant.api.out.QWeekAddPort;
import ee.qrent.billing.constant.api.out.QWeekDeletePort;
import ee.qrent.billing.constant.api.out.QWeekUpdatePort;
import ee.qrent.billing.constant.persistence.mapper.QWeekAdapterMapper;
import ee.qrent.billing.constant.persistence.repository.QWeekRepository;
import ee.qrent.billing.constant.domain.QWeek;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QWeekPersistenceAdapter implements QWeekAddPort, QWeekUpdatePort, QWeekDeletePort {

  private final QWeekRepository repository;
  private final QWeekAdapterMapper mapper;

  @Override
  public QWeek add(final QWeek domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public QWeek update(final QWeek domain) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(domain)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
