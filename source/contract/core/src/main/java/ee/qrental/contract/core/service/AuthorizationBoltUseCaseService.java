package ee.qrental.contract.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrental.contract.api.in.request.*;
import ee.qrental.contract.api.in.usecase.*;
import ee.qrental.contract.api.out.*;
import ee.qrental.contract.core.mapper.AuthorizationBoltAddRequestMapper;
import ee.qrental.contract.core.mapper.AuthorizationBoltUpdateRequestMapper;
import ee.qrental.contract.core.validator.AuthorizationBoltAddBusinessRuleValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class AuthorizationBoltUseCaseService
    implements AuthorizationBoltAddUseCase,
        AuthorizationBoltUpdateUseCase,
        AuthorizationBoltDeleteUseCase {

  private final AuthorizationBoltAddPort addPort;
  private final AuthorizationBoltUpdatePort updatePort;
  private final AuthorizationBoltDeletePort deletePort;
  private final AuthorizationBoltLoadPort loadPort;
  private final AuthorizationBoltAddRequestMapper addRequestMapper;
  private final AuthorizationBoltUpdateRequestMapper updateRequestMapper;
  private final AuthorizationBoltAddBusinessRuleValidator addBusinessRuleValidator;

  @Override
  public Long add(final AuthorizationBoltAddRequest request) {
    final var violationsCollector = addBusinessRuleValidator.validateAdd(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }

    final var contract = addRequestMapper.toDomain(request);

    final var savedContract = addPort.add(contract);

    return savedContract.getId();
  }

  @Override
  public void update(final AuthorizationBoltUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Transactional
  @Override
  public void delete(final AuthorizationBoltDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Bolt Authority failed. No Record with id = " + id);
    }
  }
}
