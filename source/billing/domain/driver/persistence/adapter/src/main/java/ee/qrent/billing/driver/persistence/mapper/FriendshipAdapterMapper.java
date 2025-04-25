package ee.qrent.billing.driver.persistence.mapper;

import ee.qrent.billing.driver.domain.Friendship;
import ee.qrent.billing.driver.persistence.entity.jakarta.FriendshipJakartaEntity;
import ee.qrent.billing.driver.persistence.entity.jakarta.DriverJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FriendshipAdapterMapper {

  public Friendship mapToDomain(final FriendshipJakartaEntity entity) {
    if (entity == null) {
      return null;
    }

    return Friendship.builder()
        .id(entity.getId())
        .friendId(entity.getFriend().getId())
        .driverId(entity.getDriver().getId())
        .startDate(entity.getDateStart())
        .build();
  }

  public FriendshipJakartaEntity mapToEntity(final Friendship domain) {

    return FriendshipJakartaEntity.builder()
        .id(domain.getId())
        .driver(DriverJakartaEntity.builder().id(domain.getDriverId()).build())
        .friend(DriverJakartaEntity.builder().id(domain.getFriendId()).build())
        .dateStart(domain.getStartDate())
        .build();
  }
}
