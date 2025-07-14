package ee.qrent.billing.e2e;

import static ee.qrent.billing.e2e.helper.CarHelper.getValidCarAddRequest;
import static org.junit.jupiter.api.Assertions.*;

import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.api.in.request.CarDeleteRequest;
import ee.qrent.billing.car.api.in.usecase.CarAddUseCase;

import ee.qrent.billing.car.api.in.usecase.CarDeleteUseCase;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CarIntegrationTest extends AbstractIntegrationTest {

  @Autowired CarAddUseCase addUseCase;
  @Autowired CarDeleteUseCase deleteUseCase;
  @Autowired GetCarQuery query;

  @Test
  void shouldAddCar() {
    // Given
    final var validCarAddRequest = getValidCarAddRequest();

    // When
    final var savedId = addUseCase.add(validCarAddRequest);

    // Then
    final var savedCar = query.getById(savedId);

    assertNotNull(savedCar);
  }

  @Test
  void shouldDeleteCar() {
    // Given
    final var savedId = addUseCase.add(getValidCarAddRequest());

    // When
    deleteUseCase.delete(new CarDeleteRequest(savedId));

    // Then
    Exception expectedException = null;
    try {
      query.getById(savedId);
    } catch (Exception e) {
      expectedException = e;
    }
    assertNotNull(expectedException);
    assertEquals(EntityNotFoundException.class, expectedException.getCause().getClass());
  }
}
