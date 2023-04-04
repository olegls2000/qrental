package ee.qrental.transaction.core.service;

import ee.qrental.transaction.api.in.request.TransactionAddRequest;
import ee.qrental.transaction.api.in.request.TransactionDeleteRequest;
import ee.qrental.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrental.transaction.api.in.usecase.TransactionAddUseCase;
import ee.qrental.transaction.api.in.usecase.TransactionDeleteUseCase;
import ee.qrental.transaction.api.in.usecase.TransactionUpdateUseCase;
import ee.qrental.transaction.api.out.TransactionAddPort;
import ee.qrental.transaction.api.out.TransactionDeletePort;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.api.out.TransactionUpdatePort;
import ee.qrental.transaction.core.mapper.TransactionAddRequestMapper;
import ee.qrental.transaction.core.mapper.TransactionUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionUseCaseService
    implements TransactionAddUseCase, TransactionUpdateUseCase, TransactionDeleteUseCase {

  private final TransactionAddPort addPort;
  private final TransactionUpdatePort updatePort;
  private final TransactionDeletePort deletePort;
  private final TransactionLoadPort loadPort;
  private final TransactionAddRequestMapper addRequestMapper;
  private final TransactionUpdateRequestMapper updateRequestMapper;

  @Override
  public Long add(final TransactionAddRequest request) {
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final TransactionUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Transaction failed. No Transaction with id = " + id);
    }
  }

  @Override
  public void delete(final TransactionDeleteRequest request) {
    deletePort.delete(request.getId());
  }
}
