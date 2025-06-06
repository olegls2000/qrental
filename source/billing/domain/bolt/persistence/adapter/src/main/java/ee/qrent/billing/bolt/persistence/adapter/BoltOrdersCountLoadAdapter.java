package ee.qrent.billing.bolt.persistence.adapter;

import ee.qrent.billing.bolt.api.out.BoltRidesCountLoadPort;
import ee.qrent.billing.bolt.domain.BoltOrdersCount;

import ee.qrent.billing.bolt.persistence.mapper.BoltOrdersCountAdapterMapper;

import ee.qrent.billing.bolt.persistence.repository.BoltOrdersCountRepository;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BoltOrdersCountLoadAdapter implements BoltRidesCountLoadPort {

  private final BoltOrdersCountRepository repository;
  private final BoltOrdersCountAdapterMapper mapper;

  @Override
  public BoltOrdersCount loadByDriverIdAndQWeekId(final Long driverId, final Long qWeekId) {

    return mapper.mapToDomain(repository.getByDriverIdAndQWeekId(driverId, qWeekId));
  }

  @Override
  public List<BoltOrdersCount> loadAllByYearAndMonth(final Integer year, final Integer month) {

    return repository.getAllByYearAndMonth(year, month).stream().map(mapper::mapToDomain).toList();
  }
}
