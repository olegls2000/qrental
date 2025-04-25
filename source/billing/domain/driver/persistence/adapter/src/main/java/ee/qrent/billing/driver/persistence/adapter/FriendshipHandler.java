package ee.qrent.billing.driver.persistence.adapter;

import ee.qrent.billing.driver.persistence.repository.FriendshipRepository;
import ee.qrent.billing.driver.domain.Driver;
import ee.qrent.billing.driver.persistence.entity.jakarta.DriverJakartaEntity;
import java.time.LocalDate;

import ee.qrent.billing.driver.persistence.entity.jakarta.FriendshipJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FriendshipHandler {

  private final FriendshipRepository friendshipRepository;

  public void addHandle(final Driver domain, final DriverJakartaEntity driverSaved) {
    final var friendshipToSave = getFriendshipToSave(driverSaved, domain);
    if (friendshipToSave == null) {
      return;
    }
    friendshipRepository.save(friendshipToSave);
  }

  private FriendshipJakartaEntity getFriendshipToSave(
      final DriverJakartaEntity driverJakartaEntity, final Driver domain) {
    final var friendship = domain.getFriendship();
    if (friendship == null) {
      return null;
    }

    return FriendshipJakartaEntity.builder()
        .driver(DriverJakartaEntity.builder().id(friendship.getDriverId()).build())
        .friend(driverJakartaEntity)
        .dateStart(LocalDate.now())
        .build();
  }

  public void updateHandle(final Driver domain, final DriverJakartaEntity driverSaved) {
    final var friendIdFromRequest = domain.getId();
    final var recommendedByDriverIdFromRequest = domain.getFriendship().getDriverId();
    final var friendship = friendshipRepository.findByFriendId(friendIdFromRequest);

    if (friendship == null && recommendedByDriverIdFromRequest == null) {
      System.out.printf(
          "'Recommended by' for Driver with id=%d was not assigned, no changes required",
          friendIdFromRequest);
      return;
    }

    if (recommendedByDriverIdFromRequest == null && friendship != null) {
      System.out.printf(
          "'Recommended by' for Driver with id=%d must be deleted", friendIdFromRequest);
      friendshipRepository.deleteById(friendship.getId());
      return;
    }

    if (recommendedByDriverIdFromRequest != null && friendship != null) {
      System.out.printf(
          "'Recommended by' for Driver with id=%d must be updated", friendIdFromRequest);
      friendship.setDriver(
          DriverJakartaEntity.builder().id(recommendedByDriverIdFromRequest).build());
      friendshipRepository.save(friendship);
    }
    if (recommendedByDriverIdFromRequest != null && friendship == null) {
      System.out.printf(
          "'Recommended by' for Driver with id=%d must be created", friendIdFromRequest);
      final var friendshipToSave =
          FriendshipJakartaEntity.builder()
              .friend(DriverJakartaEntity.builder().id(friendIdFromRequest).build())
              .driver(DriverJakartaEntity.builder().id(recommendedByDriverIdFromRequest).build())
              .dateStart(LocalDate.now())
              .build();
      friendshipRepository.save(friendshipToSave);
    }
  }
}
