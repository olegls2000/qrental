package ee.qrent.billing.insurance.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrent.billing.insurance.api.out.InsuranceCaseAddPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseUpdatePort;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseAddRequest;

import ee.qrent.billing.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrent.billing.insurance.api.in.usecase.InsuranceCaseAddUseCase;

import ee.qrent.billing.insurance.api.in.usecase.InsuranceCaseUpdateUseCase;
import ee.qrent.billing.insurance.core.mapper.InsuranceCaseAddRequestMapper;
import ee.qrent.billing.insurance.core.mapper.InsuranceCaseUpdateRequestMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class InsuranceCaseUseCaseService
    implements InsuranceCaseAddUseCase, InsuranceCaseUpdateUseCase {

  private final InsuranceCaseAddPort addPort;
  private final InsuranceCaseUpdatePort updatePort;
  private final InsuranceCaseAddRequestMapper addRequestMapper;
  private final InsuranceCaseUpdateRequestMapper updateRequestMapper;
  private final UpdateRequestValidator<InsuranceCaseUpdateRequest> updateRequestValidator;

  @Override
  public Long add(final InsuranceCaseAddRequest request) {
    final var insuranceCase = addRequestMapper.toDomain(request);
    final var savedInsuranceCase = addPort.add(insuranceCase);

    return savedInsuranceCase.getId();
  }

  @Override
  public void update(final InsuranceCaseUpdateRequest request) {
    final var violationsCollector = updateRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    updatePort.update(updateRequestMapper.toDomain(request));
  }
}
