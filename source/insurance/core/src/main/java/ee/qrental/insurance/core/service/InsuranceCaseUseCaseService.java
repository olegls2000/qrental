package ee.qrental.insurance.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrental.insurance.api.in.request.InsuranceCaseAddRequest;
import ee.qrental.insurance.api.in.request.InsuranceCaseDeleteRequest;
import ee.qrental.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrental.insurance.api.in.usecase.InsuranceCaseAddUseCase;
import ee.qrental.insurance.api.in.usecase.InsuranceCaseDeleteUseCase;
import ee.qrental.insurance.api.in.usecase.InsuranceCaseUpdateUseCase;
import ee.qrental.insurance.api.out.InsuranceCaseAddPort;
import ee.qrental.insurance.api.out.InsuranceCaseDeletePort;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrental.insurance.core.mapper.InsuranceCaseAddRequestMapper;
import ee.qrental.insurance.core.mapper.InsuranceCaseUpdateRequestMapper;
import ee.qrental.insurance.core.validator.InsuranceCaseAddBusinessRuleValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class InsuranceCaseUseCaseService
    implements InsuranceCaseAddUseCase, InsuranceCaseUpdateUseCase, InsuranceCaseDeleteUseCase {

  private final InsuranceCaseAddPort addPort;
  private final InsuranceCaseUpdatePort updatePort;
  private final InsuranceCaseDeletePort deletePort;
  private final InsuranceCaseLoadPort loadPort;
  private final InsuranceCaseAddRequestMapper addRequestMapper;
  private final InsuranceCaseUpdateRequestMapper updateRequestMapper;
  private final InsuranceCaseAddBusinessRuleValidator businessRuleValidator;

  @Override
  public Long add(final InsuranceCaseAddRequest request) {
    final var violationsCollector = businessRuleValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return null;
    }
    final var insuranceCase = addRequestMapper.toDomain(request);
    final var savedInsuranceCase = addPort.add(insuranceCase);

    return savedInsuranceCase.getId();
  }

  @Override
  public void update(final InsuranceCaseUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Transactional
  @Override
  public void delete(final InsuranceCaseDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of InsuranceCase failed. No Record with id = " + id);
    }
  }
}
