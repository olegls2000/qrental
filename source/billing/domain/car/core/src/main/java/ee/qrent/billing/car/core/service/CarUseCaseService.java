package ee.qrent.billing.car.core.service;

import ee.qrent.billing.car.api.in.request.CarAddRequest;
import ee.qrent.billing.car.api.in.request.CarDeleteRequest;
import ee.qrent.billing.car.api.in.request.CarUpdateRequest;
import ee.qrent.billing.car.api.in.usecase.CarAddUseCase;
import ee.qrent.billing.car.api.in.usecase.CarDeleteUseCase;
import ee.qrent.billing.car.api.in.usecase.CarUpdateUseCase;
import ee.qrent.billing.car.api.out.CarAddPort;
import ee.qrent.billing.car.api.out.CarDeletePort;
import ee.qrent.billing.car.api.out.CarUpdatePort;
import ee.qrent.billing.car.core.mapper.CarAddRequestMapper;
import ee.qrent.billing.car.core.mapper.CarUpdateRequestMapper;
import ee.qrent.billing.car.core.validator.CarAddRequestValidator;
import ee.qrent.billing.car.core.validator.CarUpdateRequestValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarUseCaseService implements CarAddUseCase, CarUpdateUseCase, CarDeleteUseCase {

  private final CarAddPort addPort;
  private final CarUpdatePort updatePort;
  private final CarDeletePort deletePort;
  private final CarAddRequestMapper addRequestMapper;
  private final CarUpdateRequestMapper updateRequestMapper;
  private final CarAddRequestValidator addRequestValidator;
  private final CarUpdateRequestValidator updateRequestValidator;

  @Override
  public Long add(final CarAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final CarUpdateRequest request) {
    final var violationsCollector = updateRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final CarDeleteRequest request) {
    deletePort.delete(request.getId());
  }
}
