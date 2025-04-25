package ee.qrent.billing.driver.persistence.adapter;

import ee.qrent.billing.driver.persistence.mapper.FriendshipAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.FriendshipRepository;
import ee.qrent.billing.driver.api.out.FriendshipLoadPort;
import ee.qrent.billing.driver.domain.Friendship;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FriendshipLoadAdapter implements FriendshipLoadPort {

  private FriendshipRepository repository;
  private FriendshipAdapterMapper mapper;

  @Override
  public List<Friendship> loadByDriverId(final Long driverId) {
    return repository.findByDriverId(driverId).stream()
        .map(entity -> mapper.mapToDomain(entity))
        .toList();
  }

  @Override
  public List<Friendship> loadAll() {
    return repository.findAll().stream().map(entity -> mapper.mapToDomain(entity)).toList();
  }

  @Override
  public Friendship loadById(final Long id) {
    final var entity = repository.findById(id);

    return mapper.mapToDomain(entity);
  }
}
