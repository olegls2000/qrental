package ee.qrental.driver.repository.spring;

import ee.qrental.driver.entity.jakarta.FriendshipJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendshipSpringDataRepository
    extends JpaRepository<FriendshipJakartaEntity, Long> {
  List<FriendshipJakartaEntity> findByDriverId(final Long driverId);

  FriendshipJakartaEntity findByFriendId(final Long friendId);
}
