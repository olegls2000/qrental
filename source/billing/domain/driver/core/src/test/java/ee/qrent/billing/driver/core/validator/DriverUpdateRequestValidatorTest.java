package ee.qrent.billing.driver.core.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AttributeChecker;
import ee.qrent.common.core.validation.AttributeCheckerImpl;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.driver.api.in.request.DriverUpdateRequest;
import ee.qrent.billing.driver.api.out.DriverLoadPort;
import ee.qrent.billing.driver.domain.Driver;
import ee.qrent.billing.driver.domain.Friendship;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;

class DriverUpdateRequestValidatorTest {

  private DriverUpdateRequestValidator instanceUnderTest;
  private DriverLoadPort loadPort;
  private GetQWeekQuery qWeekQuery;
  private QDateTime qDateTime;
  private AttributeChecker attributeChecker;

  @BeforeEach
  void init() {
    loadPort = mock(DriverLoadPort.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    qDateTime = mock(QDateTime.class);
    attributeChecker = new AttributeCheckerImpl();
    instanceUnderTest =
        new DriverUpdateRequestValidator(attributeChecker, loadPort, qWeekQuery, qDateTime);

    when(qDateTime.getToday()).thenReturn(LocalDate.of(2025, Month.JANUARY, 15));
  }

  private DriverUpdateRequest getValidDriverUpdateRequest() {
    final var updateRequest = new DriverUpdateRequest();
    updateRequest.setId(5L);
    updateRequest.setActive(true);
    updateRequest.setHasRequiredObligation(Boolean.FALSE);
    updateRequest.setFirstName("John");
    updateRequest.setLastName("Doe");
    updateRequest.setTaxNumber(48108259011L);
    updateRequest.setAddress("Tallinn, Str. Lootsa, 45b");
    updateRequest.setDriverLicenseNumber("AR-TY_45879");
    updateRequest.setDriverLicenseExp(qDateTime.getToday().plus(2, ChronoUnit.DAYS));
    updateRequest.setTaxiLicense("TLL-T-958");
    updateRequest.setPhone("+3729876587, +383050478955");
    updateRequest.setEmail("test.f.l@gmail.com");
    updateRequest.setCompanyName("Driver&Company");
    updateRequest.setRegNumber("REG-7878");
    updateRequest.setCompanyAddress("Tallinn, Str. Lootsa, 88c");
    updateRequest.setCompanyVat("VAT-8989");
    updateRequest.setCompanyCeoFirstName("CEO_FN");
    updateRequest.setCompanyCeoLastName("CEO_LN");
    updateRequest.setCompanyCeoTaxNumber(48108259011L);
    updateRequest.setComment("This is a comment");

    return updateRequest;
  }

  //@Test
  public void testIfDriverDidNotHaveRecommendationAndNoNewRecommendationInRequest() {
    // given
    final var updateRequest = getValidDriverUpdateRequest();
    final var driverIdToUpdate = updateRequest.getId();
    updateRequest.setRecommendedByDriverId(null);
    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .friendship(null)
                .taxNumber(updateRequest.getTaxNumber())
                .build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationCollector.hasViolations());
  }

 // @Test
  public void testIfDriverHasRecommendationAndSameRecommendationInRequest() {
    // given
    final var updateRequest = getValidDriverUpdateRequest();
    updateRequest.setRecommendedByDriverId(44L);
    final var driverIdToUpdate = updateRequest.getId();
    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .taxNumber(updateRequest.getTaxNumber())
                .friendship(Friendship.builder().driverId(44L).friendId(5L).build())
                .build());

    // when
    final var violationCollector = instanceUnderTest.validate(updateRequest);

    // then
    assertFalse(violationCollector.hasViolations());
  }

  //@Test
  public void testIfDriverHasRecommendationAndNewRecommendationInRequestAndWeekIsNotPassed() {
    // given
    final var updateRequest = getValidDriverUpdateRequest();
    updateRequest.setRecommendedByDriverId(44L);
    final var driverIdToUpdate = updateRequest.getId();
    final var dbRecommendedByDriverId = 33L;
    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .createdDate(LocalDate.of(2024, Month.JANUARY, 1)) // MONDAY
                .taxNumber(updateRequest.getTaxNumber())
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

  //@Test
  public void testIfDriverHasRecommendationAndNewRecommendationInRequestAndWeekIsPassed() {
    // given
    final var requestRecommendedByDriverId = 44L;
    final var dbRecommendedByDriverId = 33L;
    final var updateRequest = getValidDriverUpdateRequest();
    updateRequest.setRecommendedByDriverId(requestRecommendedByDriverId);
    final var driverIdToUpdate = updateRequest.getId();
    when(loadPort.loadById(driverIdToUpdate))
        .thenReturn(
            Driver.builder()
                .id(driverIdToUpdate)
                .createdDate(LocalDate.of(2024, Month.JANUARY, 1)) // MONDAY
                .taxNumber(updateRequest.getTaxNumber())
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
