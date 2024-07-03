package ee.qrental.driver.adapter.adapter;

import ee.qrental.driver.adapter.repository.CallSignLinkRepository;
import ee.qrental.driver.adapter.repository.CallSignRepository;
import ee.qrental.driver.adapter.repository.FriendshipRepository;
import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.domain.Friendship;
import ee.qrental.driver.entity.jakarta.CallSignLinkJakartaEntity;
import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;
import java.time.LocalDate;

import ee.qrental.driver.entity.jakarta.FriendshipJakartaEntity;
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
