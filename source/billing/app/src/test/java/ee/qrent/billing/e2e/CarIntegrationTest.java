package ee.qrent.billing.e2e;

import static ee.qrent.billing.e2e.helper.CarHelper.getValidAddRequest;
import static org.junit.jupiter.api.Assertions.*;

import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.api.in.request.CarDeleteRequest;
import ee.qrent.billing.car.api.in.usecase.CarAddUseCase;

import ee.qrent.billing.car.api.in.usecase.CarDeleteUseCase;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

class CarIntegrationTest extends AbstractIntegrationTest {

  @Autowired private CarAddUseCase addUseCase;
  @Autowired private CarDeleteUseCase deleteUseCase;
  @Autowired private GetCarQuery query;

  @Test
  void shouldAddCar() {
    // Given
    final var validAddRequest = getValidAddRequest();

    // When
    final var savedId = addUseCase.add(validAddRequest);

    // Then
    final var saved = query.getById(savedId);

    assertNotNull(saved);
    assertEquals(savedId, saved.getId());
    assertEquals(validAddRequest.getActive(), saved.getActive());
    assertEquals("In use", saved.getStatus());
    assertEquals(validAddRequest.getQRent(), saved.getQRent());
    assertEquals(validAddRequest.getRegNumber(), saved.getRegNumber());
    assertEquals(validAddRequest.getVin(), saved.getVin());
    assertEquals(validAddRequest.getReleaseDate(), saved.getReleaseDate());
    assertEquals(validAddRequest.getManufacturer(), saved.getManufacturer());
    assertEquals(validAddRequest.getModel(), saved.getModel());
    assertEquals(validAddRequest.getAppropriation(), saved.getAppropriation());
    assertEquals(validAddRequest.getElegance(), saved.getElegance());
    assertEquals(validAddRequest.getGearType(), saved.getGearType());
    assertEquals(validAddRequest.getFuelType(), saved.getFuelType());
    assertEquals(validAddRequest.getLpg(), saved.getLpg());
    assertEquals(validAddRequest.getDateInstallLpg(), saved.getDateInstallLpg());
    assertEquals(validAddRequest.getInsuranceFirm(), saved.getInsuranceFirm());
    assertEquals(validAddRequest.getInsuranceDateStart(), saved.getInsuranceDateStart());
    assertEquals(validAddRequest.getInsuranceDateEnd(), saved.getInsuranceDateEnd());
    assertEquals(validAddRequest.getSCard(), saved.getSCard());
    assertEquals(validAddRequest.getKey2(), saved.getKey2());
    assertEquals(validAddRequest.getGps(), saved.getGps());
    assertEquals(validAddRequest.getTechnicalInspectionEnd(), saved.getTechnicalInspectionEnd());
    assertEquals(validAddRequest.getGasInspectionEnd(), saved.getGasInspectionEnd());
    assertEquals(validAddRequest.getDateEndLpg(), saved.getDateEndLpg());
    assertEquals("GREEN", saved.getInsuranceRagStatus());
    assertEquals("GREEN", saved.getTechnicalInspectionRagStatus());
    assertEquals("GREEN", saved.getGasInspectionRagStatus());
    assertEquals(validAddRequest.getComment(), saved.getComment());
    assertEquals(validAddRequest.getBrandingQrent(), saved.getBrandingQrent());
    assertEquals(validAddRequest.getBrandingBolt(), saved.getBrandingBolt());
    assertEquals(validAddRequest.getBrandingForus(), saved.getBrandingForus());
    assertEquals(validAddRequest.getBrandingUber(), saved.getBrandingUber());
    assertEquals(validAddRequest.getBrandingTallink(), saved.getBrandingTallink());
    assertEquals(1, saved.getAge());
    assertEquals("RED", saved.getWarrantyRagStatus());
    assertEquals(LocalDate.now().plusYears(1), saved.getWarrantyEndDate());
    assertEquals(12, saved.getWarrantyMonths());
  }

  @Test
  void shouldDeleteCar() {
    // Given
    final var savedId = addUseCase.add(getValidAddRequest());

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
