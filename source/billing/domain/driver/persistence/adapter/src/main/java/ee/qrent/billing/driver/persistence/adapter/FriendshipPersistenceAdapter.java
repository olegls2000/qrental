package ee.qrent.billing.driver.persistence.adapter;

import ee.qrent.billing.driver.persistence.mapper.FriendshipAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.FriendshipRepository;
import ee.qrent.billing.driver.api.out.FriendshipAddPort;
import ee.qrent.billing.driver.api.out.FriendshipDeletePort;
import ee.qrent.billing.driver.api.out.FriendshipUpdatePort;
import ee.qrent.billing.driver.domain.Friendship;
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
