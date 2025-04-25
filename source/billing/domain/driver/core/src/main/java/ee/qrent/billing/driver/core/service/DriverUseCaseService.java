package ee.qrent.billing.driver.core.service;

import ee.qrent.billing.driver.api.out.DriverAddPort;
import ee.qrent.billing.driver.api.out.DriverDeletePort;
import ee.qrent.billing.driver.api.out.DriverLoadPort;
import ee.qrent.billing.driver.api.out.DriverUpdatePort;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.request.ContractUpdateRequest;
import ee.qrent.billing.contract.api.in.usecase.ContractUpdateUseCase;
import ee.qrent.billing.driver.api.in.request.DriverAddRequest;
import ee.qrent.billing.driver.api.in.request.DriverDeleteRequest;
import ee.qrent.billing.driver.api.in.request.DriverUpdateRequest;
import ee.qrent.billing.driver.api.in.usecase.DriverAddUseCase;
import ee.qrent.billing.driver.api.in.usecase.DriverDeleteUseCase;
import ee.qrent.billing.driver.api.in.usecase.DriverUpdateUseCase;
import ee.qrent.billing.driver.core.mapper.DriverAddRequestMapper;
import ee.qrent.billing.driver.core.mapper.DriverUpdateRequestMapper;
import ee.qrent.billing.driver.domain.Driver;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverUseCaseService
    implements DriverAddUseCase, DriverUpdateUseCase, DriverDeleteUseCase {

  private final GetContractQuery contractQuery;
  private final ContractUpdateUseCase contractUpdateUseCase;
  private final DriverAddPort driverAddPort;
  private final DriverUpdatePort updatePort;
  private final DriverDeletePort deletePort;
  private final DriverLoadPort loadPort;
  private final DriverAddRequestMapper addRequestMapper;
  private final DriverUpdateRequestMapper updateRequestMapper;
  private final AddRequestValidator<DriverAddRequest> addRequestValidator;
  private final UpdateRequestValidator<DriverUpdateRequest> updateRequestValidator;
  private final DeleteRequestValidator<DriverDeleteRequest> deleteRequestValidator;

  @Transactional
  @Override
  public Long add(final DriverAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var driverId = driverAddPort.add(addRequestMapper.toDomain(request)).getId();

    return driverId;
  }

  @Transactional
  @Override
  public void update(final DriverUpdateRequest request) {
    final var driverId = request.getId();
    checkExistence(driverId);

    final var violationsCollector = updateRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    final var driverFromDb = loadPort.loadById(driverId);
    final var contractUpdateRequired = isContractUpdateRequired(request, driverFromDb);
    if (contractUpdateRequired) {
      updateContract(driverId, request.getQFirmId());
    }
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  private void updateContract(final Long driverId, final Long qFirmId) {
    final var currentActiveContract = contractQuery.getCurrentActiveByDriverId(driverId);
    if (currentActiveContract == null) {
      System.out.println("Driver has no Active Contract. Contract update not required");

      return;
    }
    final var contractUpdateRequest = new ContractUpdateRequest();
    contractUpdateRequest.setId(currentActiveContract.getId());
    contractUpdateRequest.setQFirmId(qFirmId);

    contractUpdateUseCase.update(contractUpdateRequest);
  }

  private boolean isContractUpdateRequired(
      final DriverUpdateRequest request, final Driver driverFromDb) {
    return driverFromDb.getQFirmId() != request.getQFirmId()
        || request.getCompanyName() != driverFromDb.getCompanyName()
        || request.getCompanyCeoFirstName() != driverFromDb.getCompanyCeoFirstName()
        || request.getCompanyCeoLastName() != driverFromDb.getCompanyCeoLastName()
        || request.getCompanyCeoTaxNumber() != driverFromDb.getCompanyCeoTaxNumber()
        || request.getRegNumber() != driverFromDb.getCompanyRegistrationNumber()
        || request.getCompanyAddress() != driverFromDb.getCompanyAddress()
        || request.getFirstName() != driverFromDb.getFirstName()
        || request.getTaxNumber() != driverFromDb.getTaxNumber()
        || request.getDriverLicenseNumber() != driverFromDb.getDriverLicenseNumber()
        || request.getEmail() != driverFromDb.getEmail()
        || request.getPhone() != driverFromDb.getPhone();
  }

  @Transactional
  @Override
  public void delete(final DriverDeleteRequest request) {
    final var violationsCollector = deleteRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Driver failed. No Record with id = " + id);
    }
  }
}
