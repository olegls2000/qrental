package ee.qrental.constant.core.service;

import ee.qrental.constant.api.in.request.ConstantAddRequest;
import ee.qrental.constant.api.in.request.ConstantDeleteRequest;
import ee.qrental.constant.api.in.request.ConstantUpdateRequest;
import ee.qrental.constant.api.in.usecase.ConstantAddUseCase;
import ee.qrental.constant.api.in.usecase.ConstantDeleteUseCase;
import ee.qrental.constant.api.in.usecase.ConstantUpdateUseCase;
import ee.qrental.constant.api.out.ConstantAddPort;
import ee.qrental.constant.api.out.ConstantDeletePort;
import ee.qrental.constant.api.out.ConstantLoadPort;
import ee.qrental.constant.api.out.ConstantUpdatePort;
import ee.qrental.constant.core.mapper.ConstantAddRequestMapper;
import ee.qrental.constant.core.mapper.ConstantUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConstantUseCaseService
    implements ConstantAddUseCase, ConstantUpdateUseCase, ConstantDeleteUseCase {

  private final ConstantAddPort addPort;
  private final ConstantUpdatePort updatePort;
  private final ConstantDeletePort deletePort;
  private final ConstantLoadPort loadPort;
  private final ConstantAddRequestMapper addRequestMapper;
  private final ConstantUpdateRequestMapper updateRequestMapper;

  @Override
  public Long add(final ConstantAddRequest request) {
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final ConstantUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final ConstantDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Constant failed. No Record with id = " + id);
    }
  }
}
