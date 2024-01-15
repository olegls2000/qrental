package ee.qrental.transaction.core.service;

import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.request.TransactionDeleteRequest;
import ee.qrental.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.in.usecase.TransactionDeleteUseCase;
import ee.qrental.transaction.api.in.usecase.TransactionUpdateUseCase;
import ee.qrental.transaction.api.out.TransactionAddPort;
import ee.qrental.transaction.api.out.TransactionDeletePort;
import ee.qrental.transaction.api.out.TransactionUpdatePort;
import ee.qrental.transaction.core.mapper.TransactionAddRequestMapper;
import ee.qrental.transaction.core.mapper.TransactionUpdateRequestMapper;
import ee.qrental.transaction.core.validator.TransactionAddBusinessRuleValidator;
import ee.qrental.transaction.core.validator.TransactionUpdateDeleteBusinessRuleValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionUseCaseService
    implements TransactionAddUseCase, TransactionUpdateUseCase, TransactionDeleteUseCase {

  private final TransactionAddPort addPort;
  private final TransactionUpdatePort updatePort;
  private final TransactionDeletePort deletePort;
  private final TransactionAddRequestMapper addRequestMapper;
  private final TransactionUpdateRequestMapper updateRequestMapper;
  private final TransactionUpdateDeleteBusinessRuleValidator businessRuleValidator;
  private final TransactionAddBusinessRuleValidator addBusinessRuleValidator;

  @Override
  public Long add(final TransactionAddRequest request) {
    final var violationsCollector = addBusinessRuleValidator.validateAdd(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final TransactionUpdateRequest request) {
    final var domain = updateRequestMapper.toDomain(request);
    final var violationsCollector = businessRuleValidator.validateUpdate(domain);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final TransactionDeleteRequest request) {
    final var transactionId = request.getId();
    final var violationsCollector = businessRuleValidator.validateDelete(transactionId);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    deletePort.delete(transactionId);
  }
}
