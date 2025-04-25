package ee.qrent.billing.transaction.core.service.type;

import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeAddRequest;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeDeleteRequest;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrent.billing.transaction.api.in.usecase.type.TransactionTypeAddUseCase;
import ee.qrent.billing.transaction.api.in.usecase.type.TransactionTypeDeleteUseCase;
import ee.qrent.billing.transaction.api.in.usecase.type.TransactionTypeUpdateUseCase;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeAddPort;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeDeletePort;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrent.billing.transaction.api.out.type.TransactionTypeUpdatePort;
import ee.qrent.billing.transaction.core.mapper.type.TransactionTypeAddRequestMapper;
import ee.qrent.billing.transaction.core.mapper.type.TransactionTypeUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeUseCaseService
    implements TransactionTypeAddUseCase,
        TransactionTypeUpdateUseCase,
        TransactionTypeDeleteUseCase {

  private final TransactionTypeAddPort addPort;
  private final TransactionTypeUpdatePort updatePort;
  private final TransactionTypeDeletePort deletePort;
  private final TransactionTypeLoadPort loadPort;
  private final TransactionTypeAddRequestMapper addRequestMapper;
  private final TransactionTypeUpdateRequestMapper updateRequestMapper;
  private final AddRequestValidator<TransactionTypeAddRequest> addRequestValidator;
  private final UpdateRequestValidator<TransactionTypeUpdateRequest> updateRequestValidator;

  @Override
  public Long add(final TransactionTypeAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final TransactionTypeUpdateRequest request) {
    checkExistence(request.getId());
    final var violationsCollector = updateRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final TransactionTypeDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Transaction Type failed. No Record with id = " + id);
    }
  }
}
