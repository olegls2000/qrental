package ee.qrental.driver.core.service;

import ee.qrental.driver.api.in.request.DriverAddRequest;
import ee.qrental.driver.api.in.request.DriverDeleteRequest;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.in.usecase.DriverAddUseCase;
import ee.qrental.driver.api.in.usecase.DriverDeleteUseCase;
import ee.qrental.driver.api.in.usecase.DriverUpdateUseCase;
import ee.qrental.driver.api.out.*;
import ee.qrental.driver.core.mapper.DriverAddRequestMapper;
import ee.qrental.driver.core.mapper.DriverUpdateRequestMapper;
import ee.qrental.driver.core.validator.DriverUpdateBusinessRuleValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverUseCaseService
    implements DriverAddUseCase, DriverUpdateUseCase, DriverDeleteUseCase {

  private final DriverAddPort driverAddPort;
  private final DriverUpdatePort updatePort;
  private final DriverDeletePort deletePort;
  private final DriverLoadPort loadPort;
  private final DriverAddRequestMapper addRequestMapper;
  private final DriverUpdateRequestMapper updateRequestMapper;
  private final DriverUpdateBusinessRuleValidator updateBusinessRuleValidator;

  @Transactional
  @Override
  public Long add(final DriverAddRequest request) {
    final var driverId = driverAddPort.add(addRequestMapper.toDomain(request)).getId();

    return driverId;
  }

  @Transactional
  @Override
  public void update(final DriverUpdateRequest request) {
    checkExistence(request.getId());
    final var violationsCollector = updateBusinessRuleValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Transactional
  @Override
  public void delete(final DriverDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Driver failed. No Record with id = " + id);
    }
  }
}
