package ee.qrental.driver.core.mapper;

import ee.qrent.common.in.mapper.ResponseMapper;

import ee.qrental.driver.api.in.response.FriendshipResponse;

import ee.qrental.driver.domain.Friendship;

public class FriendshipResponseMapper implements ResponseMapper<FriendshipResponse, Friendship> {
  @Override
  public FriendshipResponse toResponse(final Friendship domain) {
    return FriendshipResponse.builder()
        .driverId(domain.getDriverId())
        .friendId(domain.getFriendId())
        .createdDate(domain.getStartDate())
        .build();
  }

  @Override
  public String toObjectInfo(final Friendship domain) {
    return "Friendship between driver " + domain.getDriverId() + " and friend: " + domain.getFriendId();
  }
}
