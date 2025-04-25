package ee.qrent.billing.firm.core.service;

import ee.qrent.billing.firm.api.in.request.FirmAddRequest;
import ee.qrent.billing.firm.api.in.request.FirmDeleteRequest;
import ee.qrent.billing.firm.api.in.request.FirmUpdateRequest;
import ee.qrent.billing.firm.api.in.usecase.FirmAddUseCase;
import ee.qrent.billing.firm.api.in.usecase.FirmDeleteUseCase;
import ee.qrent.billing.firm.api.in.usecase.FirmUpdateUseCase;
import ee.qrent.billing.firm.api.out.FirmAddPort;
import ee.qrent.billing.firm.api.out.FirmDeletePort;
import ee.qrent.billing.firm.api.out.FirmLoadPort;
import ee.qrent.billing.firm.api.out.FirmUpdatePort;
import ee.qrent.billing.firm.core.mapper.FirmAddRequestMapper;
import ee.qrent.billing.firm.core.mapper.FirmUpdateRequestMapper;
import ee.qrent.billing.firm.core.validator.FirmRequestValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmUseCaseService implements FirmAddUseCase, FirmUpdateUseCase, FirmDeleteUseCase {

  private final FirmAddPort addPort;
  private final FirmUpdatePort updatePort;
  private final FirmDeletePort deletePort;
  private final FirmLoadPort loadPort;
  private final FirmAddRequestMapper addRequestMapper;
  private final FirmUpdateRequestMapper updateRequestMapper;
  private final FirmRequestValidator requestValidator;

  @Override
  public Long add(final FirmAddRequest request) {
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final FirmUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final FirmDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Driver failed. No Record with id = " + id);
    }
  }
}
