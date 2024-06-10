package ee.qrental.driver.adapter.adapter;

import ee.qrental.driver.adapter.mapper.FriendshipAdapterMapper;
import ee.qrental.driver.adapter.repository.FriendshipRepository;
import ee.qrental.driver.api.out.FriendshipAddPort;
import ee.qrental.driver.api.out.FriendshipDeletePort;
import ee.qrental.driver.api.out.FriendshipUpdatePort;
import ee.qrental.driver.domain.Friendship;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FriendshipPersistenceAdapter
    implements FriendshipAddPort, FriendshipUpdatePort, FriendshipDeletePort {
  private final FriendshipRepository friendshipRepository;
  private final FriendshipAdapterMapper friendshipAdapterMapper;

  @Override
  public void delete(final Long id) {}

  @Override
  public Friendship update(final Friendship domain) {
    return null;
  }

  @Override
  public Friendship add(final Friendship domain) {
    final var entityToSave = friendshipAdapterMapper.mapToEntity(domain);
    final var entitySaved = friendshipRepository.save(entityToSave);

    return friendshipAdapterMapper.mapToDomain(entitySaved);
  }
}
