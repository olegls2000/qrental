package ee.qrental.contract.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrental.contract.api.in.request.*;
import ee.qrental.contract.api.in.usecase.*;
import ee.qrental.contract.api.out.*;
import ee.qrental.contract.core.mapper.AuthorizationAddRequestMapper;
import ee.qrental.contract.core.mapper.AuthorizationUpdateRequestMapper;
import ee.qrental.contract.core.validator.AuthorizationAddBusinessRuleValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class AuthorizationUseCaseService
    implements AuthorizationAddUseCase,
        AuthorizationUpdateUseCase,
        AuthorizationDeleteUseCase {

  private final AuthorizationBoltAddPort addPort;
  private final AuthorizationBoltUpdatePort updatePort;
  private final AuthorizationBoltDeletePort deletePort;
  private final AuthorizationLoadPort loadPort;
  private final AuthorizationAddRequestMapper addRequestMapper;
  private final AuthorizationUpdateRequestMapper updateRequestMapper;
  private final AuthorizationAddBusinessRuleValidator addBusinessRuleValidator;

  @Override
  public Long add(final AuthorizationAddRequest request) {
    final var violationsCollector = addBusinessRuleValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var authorization = addRequestMapper.toDomain(request);
    final var savedAuthorization = addPort.add(authorization);

    return savedAuthorization.getId();
  }

  @Override
  public void update(final AuthorizationUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Transactional
  @Override
  public void delete(final AuthorizationDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Bolt Authority failed. No Record with id = " + id);
    }
  }
}
