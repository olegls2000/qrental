package ee.qrental.constant.adapter.adapter;

import ee.qrental.constant.adapter.mapper.QWeekAdapterMapper;
import ee.qrental.constant.adapter.repository.QWeekRepository;
import ee.qrental.constant.api.out.*;
import ee.qrental.constant.domain.QWeek;
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
