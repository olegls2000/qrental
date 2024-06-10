package ee.qrental.driver.core.validator;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.domain.Friendship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DriverUpdateBusinessRuleValidatorTest {

  private DriverUpdateBusinessRuleValidator instanceUnderTest;
  private DriverLoadPort loadPort;

  @BeforeEach
  void init() {
    loadPort = mock(DriverLoadPort.class);
    instanceUnderTest = new DriverUpdateBusinessRuleValidator(loadPort);
  }

  @Test
  public void testIfDriverDidNotHaveFriendshipBeforeUpdate() {
    // given
    final var driverIdToUpdate = 5L;
    final var updateRequest = DriverUpdateRequest.builder().id(driverIdToUpdate).build();
    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(Driver.builder().id(driverIdToUpdate).friendship(null).build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  @Test
  public void testIfNoUpdatesForFriendship() {
    // given
    final var driverIdToUpdate = 5L;
    final var requestRecommendedByDriverId = 11L;
    final var dbRecommendedByDriverId = 10L;
    final var updateRequest =
        DriverUpdateRequest.builder()
            .id(driverIdToUpdate)
            .recommendedByDriverId(requestRecommendedByDriverId)
            .build();

    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .friendship(
                    Friendship.builder()
                        .driverId(dbRecommendedByDriverId)
                        .friendId(driverIdToUpdate)
                        .build())
                .build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  @Test
  public void testIfDriversFriendshipWasCreatedOnCurrentWeek() {
    // given
    final var driverIdToUpdate = 5L;
    final var requestRecommendedByDriverId = 11L;
    final var dbRecommendedByDriverId = 10L;

    final var updateRequest =
        DriverUpdateRequest.builder()
            .id(driverIdToUpdate)
            .recommendedByDriverId(requestRecommendedByDriverId)
            .build();
    final var friendshipStartDate = LocalDate.now().minus(6, DAYS);

    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .friendship(
                    Friendship.builder()
                        .driverId(dbRecommendedByDriverId)
                        .friendId(driverIdToUpdate)
                        .startDate(friendshipStartDate)
                        .build())
                .build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  @Test
  public void testIfDriversFriendshipWasCreatedOnPreviousWeek() {
    // given
    final var driverIdToUpdate = 5L;
    final var requestRecommendedByDriverId = 11L;
    final var dbRecommendedByDriverId = 10L;

    final var updateRequest =
        DriverUpdateRequest.builder()
            .id(driverIdToUpdate)
            .recommendedByDriverId(requestRecommendedByDriverId)
            .build();
    final var friendshipStartDate = LocalDate.now().minus(7, DAYS);

    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .friendship(
                    Friendship.builder()
                        .driverId(dbRecommendedByDriverId)
                        .friendId(driverIdToUpdate)
                        .startDate(friendshipStartDate)
                        .build())
                .build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationCollector.hasViolations());
  }
}
