package ee.qrent.billing.bolt.persistence.adapter;

import ee.qrent.billing.bolt.api.out.BoltOrdersCountLoadPort;
import ee.qrent.billing.bolt.domain.BoltOrdersCount;

import ee.qrent.billing.bolt.persistence.mapper.BoltOrdersCountAdapterMapper;

import ee.qrent.billing.bolt.persistence.repository.BoltOrdersCountRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoltOrdersCountLoadAdapter implements BoltOrdersCountLoadPort {

  private final BoltOrdersCountRepository repository;
  private final BoltOrdersCountAdapterMapper mapper;

  @Override
  public BoltOrdersCount loadByDriverIdAndQWeekId(Long driverId, Long qWeekId) {

    return mapper.mapToDomain(repository.getByDriverIdAndQWeekId(driverId, qWeekId));
  }
}
