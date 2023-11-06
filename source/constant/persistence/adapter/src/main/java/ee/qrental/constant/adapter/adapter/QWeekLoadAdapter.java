package ee.qrental.constant.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.constant.adapter.mapper.QWeekAdapterMapper;
import ee.qrental.constant.adapter.repository.QWeekRepository;
import ee.qrental.constant.api.out.QWeekLoadPort;
import ee.qrental.constant.domain.QWeek;
import java.util.List;
import lombok.AllArgsConstructor;

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
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public List<Integer> loadYears() {
    return repository.findAllYears();
  }

  @Override
  public QWeek loadByYearAndNumber(final Integer year, final Integer number) {
    return mapper.mapToDomain(repository.findByYearAndWeekNumber(year, number));
  }
}
