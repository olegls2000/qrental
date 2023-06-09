package ee.qrental.transaction.core.service.type;

import ee.qrental.transaction.api.in.request.type.TransactionTypeAddRequest;
import ee.qrental.transaction.api.in.request.type.TransactionTypeDeleteRequest;
import ee.qrental.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrental.transaction.api.in.usecase.type.TransactionTypeAddUseCase;
import ee.qrental.transaction.api.in.usecase.type.TransactionTypeDeleteUseCase;
import ee.qrental.transaction.api.in.usecase.type.TransactionTypeUpdateUseCase;
import ee.qrental.transaction.api.out.type.TransactionTypeAddPort;
import ee.qrental.transaction.api.out.type.TransactionTypeDeletePort;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.api.out.type.TransactionTypeUpdatePort;
import ee.qrental.transaction.core.mapper.type.TransactionTypeAddRequestMapper;
import ee.qrental.transaction.core.mapper.type.TransactionTypeUpdateRequestMapper;
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

  @Override
  public Long add(final TransactionTypeAddRequest request) {
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final TransactionTypeUpdateRequest request) {
    checkExistence(request.getId());
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
