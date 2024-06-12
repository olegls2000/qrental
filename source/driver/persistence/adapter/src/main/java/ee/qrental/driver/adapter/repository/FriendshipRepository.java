package ee.qrental.driver.adapter.repository;

import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;
import ee.qrental.driver.entity.jakarta.FriendshipJakartaEntity;

import java.util.List;

public interface FriendshipRepository {

  FriendshipJakartaEntity save(final FriendshipJakartaEntity entity);

  FriendshipJakartaEntity findById(final Long id);

  List<FriendshipJakartaEntity> findAll();

  FriendshipJakartaEntity findByFriendId(final Long friendId);

  List<FriendshipJakartaEntity> findByDriverId(final Long driverId);

  void deleteById(final Long id);
}
