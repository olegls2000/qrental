package ee.qrental.driver.core.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrent.common.in.time.QDateTime;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.domain.Driver;
import ee.qrental.driver.domain.Friendship;
import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DriverUpdateBusinessRuleValidatorTest {

  private DriverUpdateBusinessRuleValidator instanceUnderTest;
  private DriverLoadPort loadPort;
  private GetQWeekQuery qWeekQuery;
  private QDateTime qDateTime;

  @BeforeEach
  void init() {
    loadPort = mock(DriverLoadPort.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    qDateTime = mock(QDateTime.class);
    instanceUnderTest = new DriverUpdateBusinessRuleValidator(loadPort, qWeekQuery, qDateTime);
  }

  @Test
  public void testIfDriverDidNotHaveRecommendationAndNoNewRecommendationInRequest() {
    // given
    final var driverIdToUpdate = 5L;
    final var updateRequest =
        DriverUpdateRequest.builder().id(driverIdToUpdate).recommendedByDriverId(null).build();
    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(Driver.builder().id(driverIdToUpdate).friendship(null).build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  @Test
  public void testIfDriverHasRecommendationAndSameRecommendationInRequest() {
    // given
    final var driverIdToUpdate = 5L;
    final var updateRequest =
        DriverUpdateRequest.builder().id(driverIdToUpdate).recommendedByDriverId(44L).build();
    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .friendship(Friendship.builder().driverId(44L).friendId(5L).build())
                .build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  @Test
  public void testIfDriverHasRecommendationAndNewRecommendationInRequestAndWeekIsNotPassed() {
    // given
    final var driverIdToUpdate = 5L;
    final var requestRecommendedByDriverId = 44L;
    final var dbRecommendedByDriverId = 33L;
    final var updateRequest =
        DriverUpdateRequest.builder()
            .id(driverIdToUpdate)
            .recommendedByDriverId(requestRecommendedByDriverId)
            .build();

    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .createdDate(LocalDate.of(2024, Month.JANUARY, 1)) // MONDAY
                .friendship(
                    Friendship.builder()
                        .driverId(dbRecommendedByDriverId)
                        .friendId(driverIdToUpdate)
                        .build())
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2024, Month.JANUARY, 7)); // SUNDAY
    when(qWeekQuery.getByYearAndNumber(2024, 1))
        .thenReturn(
            QWeekResponse.builder()
                .number(1)
                .year(2024)
                .start(LocalDate.of(2024, Month.JANUARY, 1))
                .end(LocalDate.of(2024, Month.JANUARY, 7))
                .build()); // SUNDAY

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  @Test
  public void testIfDriverHasRecommendationAndNewRecommendationInRequestAndWeekIsPassed() {
    // given
    final var driverIdToUpdate = 5L;
    final var requestRecommendedByDriverId = 44L;
    final var dbRecommendedByDriverId = 33L;
    final var updateRequest =
        DriverUpdateRequest.builder()
            .id(driverIdToUpdate)
            .recommendedByDriverId(requestRecommendedByDriverId)
            .build();

    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .createdDate(LocalDate.of(2024, Month.JANUARY, 1)) // MONDAY
                .friendship(
                    Friendship.builder()
                        .driverId(dbRecommendedByDriverId)
                        .friendId(driverIdToUpdate)
                        .build())
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2024, Month.JANUARY, 8)); // SUNDAY
    when(qWeekQuery.getByYearAndNumber(2024, 1))
        .thenReturn(
            QWeekResponse.builder()
                .number(1)
                .year(2024)
                .start(LocalDate.of(2024, Month.JANUARY, 1))
                .end(LocalDate.of(2024, Month.JANUARY, 7))
                .build()); // SUNDAY

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertTrue(violationCollector.hasViolations());
  }
}
