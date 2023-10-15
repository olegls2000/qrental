package ee.qrental.car.core.service;

import ee.qrental.car.api.in.request.CarLinkAddRequest;
import ee.qrental.car.api.in.request.CarLinkDeleteRequest;
import ee.qrental.car.api.in.request.CarLinkUpdateRequest;
import ee.qrental.car.api.in.usecase.CarLinkAddUseCase;
import ee.qrental.car.api.in.usecase.CarLinkDeleteUseCase;
import ee.qrental.car.api.in.usecase.LinkUpdateUseCase;
import ee.qrental.car.api.out.CarLinkAddPort;
import ee.qrental.car.api.out.CarLinkDeletePort;
import ee.qrental.car.api.out.CarLinkLoadPort;
import ee.qrental.car.api.out.CarLinkUpdatePort;
import ee.qrental.car.core.mapper.CarLinkAddRequestMapper;
import ee.qrental.car.core.mapper.CarLinkUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarLinkUseCaseService implements CarLinkAddUseCase, LinkUpdateUseCase, CarLinkDeleteUseCase {

  private final CarLinkAddPort addPort;
  private final CarLinkUpdatePort updatePort;
  private final CarLinkDeletePort deletePort;
  private final CarLinkLoadPort loadPort;
  private final CarLinkAddRequestMapper addRequestMapper;
  private final CarLinkUpdateRequestMapper updateRequestMapper;

  @Override
  public Long add(final CarLinkAddRequest request) {
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final CarLinkUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final CarLinkDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Link failed. No Record with id = " + id);
    }
  }
}
