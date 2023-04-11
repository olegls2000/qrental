package ee.qrental.car.core.service;

import ee.qrental.car.api.in.request.CarAddRequest;
import ee.qrental.car.api.in.request.CarDeleteRequest;
import ee.qrental.car.api.in.request.CarUpdateRequest;
import ee.qrental.car.api.in.usecase.CarAddUseCase;
import ee.qrental.car.api.in.usecase.CarDeleteUseCase;
import ee.qrental.car.api.in.usecase.CarUpdateUseCase;
import ee.qrental.car.api.out.CarAddPort;
import ee.qrental.car.api.out.CarDeletePort;
import ee.qrental.car.api.out.CarLoadPort;
import ee.qrental.car.api.out.CarUpdatePort;
import ee.qrental.car.core.mapper.CarAddRequestMapper;
import ee.qrental.car.core.mapper.CarUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarUseCaseService
    implements CarAddUseCase, CarUpdateUseCase, CarDeleteUseCase {

  private final CarAddPort addPort;
  private final CarUpdatePort updatePort;
  private final CarDeletePort deletePort;
  private final CarLoadPort loadPort;
  private final CarAddRequestMapper addRequestMapper;
  private final CarUpdateRequestMapper updateRequestMapper;

  @Override
  public Long add(final CarAddRequest request) {
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final CarUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final CarDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Car failed. No Record with id = " + id);
    }
  }
}
