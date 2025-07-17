package ee.qrent.billing.e2e;

import ee.qrent.billing.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.request.DriverDeleteRequest;
import ee.qrent.billing.driver.api.in.usecase.CallSignAddUseCase;
import ee.qrent.billing.driver.api.in.usecase.DriverAddUseCase;
import ee.qrent.billing.driver.api.in.usecase.DriverDeleteUseCase;
import ee.qrent.billing.e2e.helper.CallSignHelper;
import ee.qrent.billing.e2e.helper.DriverHelper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DriverIntegrationTest extends AbstractIntegrationTest {
  @Autowired private DriverAddUseCase driverAddUseCase;
  @Autowired private CallSignAddUseCase callSignAddUseCase;
  @Autowired private DriverDeleteUseCase driverDeleteUseCase;
  @Autowired private GetDriverQuery driverQuery;
  @Autowired private GetCallSignLinkQuery callSignLinkQuery;

  @Test
  void shouldAddDriver() {
    // Given
    final var callSignAddRequest = CallSignHelper.getValidAddRequest();
    final var savedCallSignId = callSignAddUseCase.add(callSignAddRequest);

    final var validAddRequest = DriverHelper.getValidAddRequest(null, savedCallSignId, null);

    // When
    final var savedId = driverAddUseCase.add(validAddRequest);

    // Then
    final var saved = driverQuery.getById(savedId);

    assertNotNull(saved);
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
