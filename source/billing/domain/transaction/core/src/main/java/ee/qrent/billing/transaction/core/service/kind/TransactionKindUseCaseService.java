package ee.qrent.billing.transaction.core.service.kind;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindAddRequest;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindDeleteRequest;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrent.billing.transaction.api.in.usecase.kind.TransactionKindAddUseCase;
import ee.qrent.billing.transaction.api.in.usecase.kind.TransactionKindDeleteUseCase;
import ee.qrent.billing.transaction.api.in.usecase.kind.TransactionKindUpdateUseCase;
import ee.qrent.billing.transaction.api.out.kind.TransactionKindAddPort;
import ee.qrent.billing.transaction.api.out.kind.TransactionKindDeletePort;
import ee.qrent.billing.transaction.api.out.kind.TransactionKindLoadPort;
import ee.qrent.billing.transaction.api.out.kind.TransactionKindUpdatePort;
import ee.qrent.billing.transaction.core.mapper.kind.TransactionKindAddRequestMapper;
import ee.qrent.billing.transaction.core.mapper.kind.TransactionKindUpdateRequestMapper;
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
  private final AddRequestValidator<TransactionKindAddRequest> addRequestValidator;
  private final UpdateRequestValidator<TransactionKindUpdateRequest> updateRequestValidator;

  @Override
  public Long add(final TransactionKindAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final TransactionKindUpdateRequest request) {
    checkExistence(request.getId());
    final var violationsCollector = updateRequestValidator.validate(request);
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
