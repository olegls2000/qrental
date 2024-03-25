package ee.qrental.firm.core.service;

import ee.qrental.firm.api.in.request.WeekObligationAddRequest;
import ee.qrental.firm.api.in.request.FirmDeleteRequest;
import ee.qrental.firm.api.in.request.FirmUpdateRequest;
import ee.qrental.firm.api.in.usecase.WeekObligationAddUseCase;
import ee.qrental.firm.api.in.usecase.FirmDeleteUseCase;
import ee.qrental.firm.api.in.usecase.FirmUpdateUseCase;
import ee.qrental.firm.api.out.FirmAddPort;
import ee.qrental.firm.api.out.FirmDeletePort;
import ee.qrental.firm.api.out.FirmLoadPort;
import ee.qrental.firm.api.out.FirmUpdatePort;
import ee.qrental.firm.core.mapper.FirmAddRequestMapper;
import ee.qrental.firm.core.mapper.FirmUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmUseCaseService
    implements WeekObligationAddUseCase, FirmUpdateUseCase, FirmDeleteUseCase {

  private final FirmAddPort addPort;
  private final FirmUpdatePort updatePort;
  private final FirmDeletePort deletePort;
  private final FirmLoadPort loadPort;
  private final FirmAddRequestMapper addRequestMapper;
  private final FirmUpdateRequestMapper updateRequestMapper;

  @Override
  public Long add(final WeekObligationAddRequest request) {
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
