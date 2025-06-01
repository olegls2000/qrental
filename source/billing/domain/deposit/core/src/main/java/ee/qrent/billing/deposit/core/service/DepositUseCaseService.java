package ee.qrent.billing.deposit.core.service;

import ee.qrent.billing.deposit.api.in.request.DepositAddRequest;
import ee.qrent.billing.deposit.api.in.request.DepositDeleteRequest;
import ee.qrent.billing.deposit.api.in.request.DepositUpdateRequest;
import ee.qrent.billing.deposit.api.in.usecase.DepositAddUseCase;
import ee.qrent.billing.deposit.api.in.usecase.DepositDeleteUseCase;
import ee.qrent.billing.deposit.api.in.usecase.DepositUpdateUseCase;
import ee.qrent.billing.deposit.api.out.DepositAddPort;
import ee.qrent.billing.deposit.api.out.DepositDeletePort;
import ee.qrent.billing.deposit.api.out.DepositLoadPort;
import ee.qrent.billing.deposit.api.out.DepositUpdatePort;
import ee.qrent.billing.deposit.core.mapper.DepositAddRequestMapper;
import ee.qrent.billing.deposit.core.mapper.DepositUpdateRequestMapper;
import ee.qrent.billing.deposit.core.validator.DepositRequestValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DepositUseCaseService
    implements DepositAddUseCase, DepositUpdateUseCase, DepositDeleteUseCase {

  private final DepositAddPort addPort;
  private final DepositUpdatePort updatePort;
  private final DepositDeletePort deletePort;
  private final DepositLoadPort loadPort;
  private final DepositAddRequestMapper addRequestMapper;
  private final DepositUpdateRequestMapper updateRequestMapper;
  private final DepositRequestValidator requestValidator;

  @Override
  public Long add(final DepositAddRequest request) {
    requestValidator.validate(request);
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final DepositUpdateRequest request) {
    requestValidator.validate(request);

    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final DepositDeleteRequest request) {
    requestValidator.validate(request);
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Deposit failed. No Record with id = " + id);
    }
  }
}
