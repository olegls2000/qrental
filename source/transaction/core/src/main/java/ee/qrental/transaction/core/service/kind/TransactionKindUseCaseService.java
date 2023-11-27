package ee.qrental.transaction.core.service.kind;

import ee.qrental.transaction.api.in.request.kind.TransactionKindAddRequest;
import ee.qrental.transaction.api.in.request.kind.TransactionKindDeleteRequest;
import ee.qrental.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrental.transaction.api.in.usecase.kind.TransactionKindAddUseCase;
import ee.qrental.transaction.api.in.usecase.kind.TransactionKindDeleteUseCase;
import ee.qrental.transaction.api.in.usecase.kind.TransactionKindUpdateUseCase;
import ee.qrental.transaction.api.out.kind.TransactionKindAddPort;
import ee.qrental.transaction.api.out.kind.TransactionKindDeletePort;
import ee.qrental.transaction.api.out.kind.TransactionKindLoadPort;
import ee.qrental.transaction.api.out.kind.TransactionKindUpdatePort;
import ee.qrental.transaction.core.mapper.kind.TransactionKindAddRequestMapper;
import ee.qrental.transaction.core.mapper.kind.TransactionKindUpdateRequestMapper;
import ee.qrental.transaction.core.validator.TransactionKindAddBusinessRuleValidator;
import ee.qrental.transaction.core.validator.TransactionKindUpdateBusinessRuleValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionKindUseCaseService
    implements TransactionKindAddUseCase,
        TransactionKindUpdateUseCase,
        TransactionKindDeleteUseCase {

  private final TransactionKindAddPort addPort;
  private final TransactionKindUpdatePort updatePort;
  private final TransactionKindDeletePort deletePort;
  private final TransactionKindLoadPort loadPort;
  private final TransactionKindAddRequestMapper addRequestMapper;
  private final TransactionKindUpdateRequestMapper updateRequestMapper;
  private final TransactionKindAddBusinessRuleValidator addBusinessRuleValidator;
  private final TransactionKindUpdateBusinessRuleValidator updateBusinessRuleValidator;

  @Override
  public Long add(final TransactionKindAddRequest request) {
    final var violationsCollector = addBusinessRuleValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final TransactionKindUpdateRequest request) {
    checkExistence(request.getId());
    final var violationsCollector = updateBusinessRuleValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final TransactionKindDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Transaction Kind failed. No Record with id = " + id);
    }
  }
}
