package ee.qrent.billing.e2e;

import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.request.DriverDeleteRequest;
import ee.qrent.billing.driver.api.in.usecase.CallSignAddUseCase;
import ee.qrent.billing.driver.api.in.usecase.DriverAddUseCase;
import ee.qrent.billing.driver.api.in.usecase.DriverDeleteUseCase;
import ee.qrent.billing.e2e.helper.CallSignHelper;
import ee.qrent.billing.e2e.helper.DriverHelper;
import ee.qrent.billing.e2e.helper.FirmHelper;
import ee.qrent.billing.firm.api.in.usecase.FirmAddUseCase;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DriverIntegrationTest extends AbstractIntegrationTest {
  @Autowired private DriverAddUseCase driverAddUseCase;
  @Autowired private CallSignAddUseCase callSignAddUseCase;
  @Autowired private FirmAddUseCase firmAddUseCase;
  @Autowired private DriverDeleteUseCase driverDeleteUseCase;
  @Autowired private GetDriverQuery driverQuery;
  @Autowired private GetCallSignLinkQuery callSignLinkQuery;

  @Test
  void shouldAddDriver() {
    // Given
    final var firmAddRequest = FirmHelper.getValidAddRequest();

    final var callSignAddRequestFirst = CallSignHelper.getValidAddRequest(1);
    final var savedCallSignFirstId = callSignAddUseCase.add(callSignAddRequestFirst);
    final var savedFirmId = firmAddUseCase.add(firmAddRequest);
    final var validAddRequestForDriverRecommendedBy =
        DriverHelper.getValidAddRequest(savedFirmId, savedCallSignFirstId, null);
    final var savedDriverRecommendedById =
        driverAddUseCase.add(validAddRequestForDriverRecommendedBy);

    final var callSignAddRequestSecond = CallSignHelper.getValidAddRequest(2);
    final var savedCallSignSecondId = callSignAddUseCase.add(callSignAddRequestSecond);
    final var validAddRequestForDriver =
        DriverHelper.getValidAddRequest(
            savedFirmId, savedCallSignSecondId, savedDriverRecommendedById);

    // When
    final var savedId = driverAddUseCase.add(validAddRequestForDriver);

    // Then
    final var savedDriver = driverQuery.getById(savedId);
    final var createdCallSignLink = callSignLinkQuery.getActiveCallSignLinkByDriverId(savedId);

    assertNotNull(savedDriver);
    assertNotNull(createdCallSignLink);
  }

  @Test
  void shouldDeleteCar() {
    // Given
    final var savedId = driverAddUseCase.add(DriverHelper.getValidAddRequest(null, null, null));

    // When
    driverDeleteUseCase.delete(new DriverDeleteRequest(savedId));

    // Then
    Exception expectedException = null;
    try {
      driverQuery.getById(savedId);
    } catch (Exception e) {
      expectedException = e;
    }
    assertNotNull(expectedException);
    assertEquals(EntityNotFoundException.class, expectedException.getCause().getClass());
  }
}
