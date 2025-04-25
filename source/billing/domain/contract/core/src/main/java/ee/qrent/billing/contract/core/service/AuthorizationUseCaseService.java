package ee.qrent.billing.contract.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrent.billing.contract.api.in.request.AuthorizationAddRequest;
import ee.qrent.billing.contract.api.in.request.AuthorizationDeleteRequest;
import ee.qrent.billing.contract.api.in.request.AuthorizationUpdateRequest;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationAddUseCase;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationDeleteUseCase;
import ee.qrent.billing.contract.api.in.usecase.AuthorizationUpdateUseCase;
import ee.qrent.billing.contract.api.out.AuthorizationBoltAddPort;
import ee.qrent.billing.contract.api.out.AuthorizationBoltDeletePort;
import ee.qrent.billing.contract.api.out.AuthorizationBoltUpdatePort;
import ee.qrent.billing.contract.api.out.AuthorizationLoadPort;
import ee.qrent.billing.contract.core.mapper.AuthorizationAddRequestMapper;
import ee.qrent.billing.contract.core.mapper.AuthorizationUpdateRequestMapper;
import ee.qrent.billing.contract.core.validator.AuthorizationAddRequestValidator;
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
  private final AuthorizationAddRequestValidator addRequestValidator;

  @Override
  public Long add(final AuthorizationAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
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
