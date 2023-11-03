package ee.qrental.constant.adapter.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrental.constant.adapter.mapper.ConstantAdapterMapper;
import ee.qrental.constant.adapter.mapper.QWeekAdapterMapper;
import ee.qrental.constant.adapter.repository.ConstantRepository;
import ee.qrental.constant.adapter.repository.QWeekRepository;
import ee.qrental.constant.api.out.ConstantLoadPort;
import ee.qrental.constant.api.out.QWeekLoadPort;
import ee.qrental.constant.domain.Constant;
import java.util.List;

import ee.qrental.constant.domain.QWeek;
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

}
