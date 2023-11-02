package ee.qrental.constant.core.service;

import ee.qrental.constant.api.in.request.constant.ConstantAddRequest;
import ee.qrental.constant.api.in.request.constant.ConstantDeleteRequest;
import ee.qrental.constant.api.in.request.constant.ConstantUpdateRequest;
import ee.qrental.constant.api.in.usecase.constant.ConstantAddUseCase;
import ee.qrental.constant.api.in.usecase.constant.ConstantDeleteUseCase;
import ee.qrental.constant.api.in.usecase.constant.ConstantUpdateUseCase;
import ee.qrental.constant.api.out.constant.ConstantAddPort;
import ee.qrental.constant.api.out.constant.ConstantDeletePort;
import ee.qrental.constant.api.out.constant.ConstantLoadPort;
import ee.qrental.constant.api.out.constant.ConstantUpdatePort;
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
