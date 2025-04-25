package ee.qrent.billing.constant.persistence.adapter;

import ee.qrent.billing.constant.persistence.mapper.QWeekAdapterMapper;
import ee.qrent.billing.constant.persistence.repository.QWeekRepository;
import ee.qrent.billing.constant.api.out.QWeekLoadPort;
import ee.qrent.billing.constant.domain.QWeek;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class QWeekLoadAdapter implements QWeekLoadPort {

  private final QWeekRepository repository;
  private final QWeekAdapterMapper mapper;

  @Override
  public List<QWeek> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public QWeek loadById(final Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<QWeek> loadByYear(final Integer year) {
    return repository.findByYear(year).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public List<QWeek> loadAllBetweenByIds(final Long startWeekId, final Long endWeekId) {
    return repository.findAllBetweenByIds(startWeekId, endWeekId).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }

  @Override
  public List<QWeek> loadAllBeforeById(Long id) {
    return repository.findAllBeforeById(id).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public List<QWeek> loadAllAfterById(Long id) {
    return repository.findAllAfterById(id).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public QWeek loadByYearAndNumber(final Integer year, final Integer number) {
    return mapper.mapToDomain(repository.findByYearAndWeekNumber(year, number));
  }
}
