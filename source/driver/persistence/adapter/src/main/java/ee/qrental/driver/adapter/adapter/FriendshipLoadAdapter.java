package ee.qrental.driver.adapter.adapter;

import ee.qrental.driver.adapter.mapper.FriendshipAdapterMapper;
import ee.qrental.driver.adapter.repository.FriendshipRepository;
import ee.qrental.driver.api.out.FriendshipLoadPort;
import ee.qrental.driver.domain.Friendship;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FriendshipLoadAdapter implements FriendshipLoadPort {

  private FriendshipRepository repository;
  private FriendshipAdapterMapper mapper;

  @Override
  public Friendship loadByFriendId(final Long friendId) {
    final var entity = repository.findByFriendId(friendId);

    return mapper.mapToDomain(entity);
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
