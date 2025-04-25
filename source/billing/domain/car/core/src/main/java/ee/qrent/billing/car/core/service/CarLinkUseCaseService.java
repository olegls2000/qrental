package ee.qrent.billing.car.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.car.api.in.request.CarLinkAddRequest;
import ee.qrent.billing.car.api.in.request.CarLinkDeleteRequest;
import ee.qrent.billing.car.api.in.request.CarLinkCloseRequest;
import ee.qrent.billing.car.api.in.request.CarLinkUpdateRequest;
import ee.qrent.billing.car.api.in.usecase.CarLinkAddUseCase;
import ee.qrent.billing.car.api.in.usecase.CarLinkDeleteUseCase;
import ee.qrent.billing.car.api.in.usecase.CarLinkCloseUseCase;
import ee.qrent.billing.car.api.in.usecase.CarLinkUpdateUseCase;
import ee.qrent.billing.car.api.out.CarLinkAddPort;
import ee.qrent.billing.car.api.out.CarLinkDeletePort;
import ee.qrent.billing.car.api.out.CarLinkLoadPort;
import ee.qrent.billing.car.api.out.CarLinkUpdatePort;
import ee.qrent.billing.car.core.mapper.CarLinkAddRequestMapper;
import ee.qrent.billing.car.domain.CarLink;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class CarLinkUseCaseService
    implements CarLinkAddUseCase, CarLinkUpdateUseCase, CarLinkDeleteUseCase, CarLinkCloseUseCase {

  private final CarLinkAddPort addPort;
  private final CarLinkUpdatePort updatePort;
  private final CarLinkDeletePort deletePort;
  private final CarLinkLoadPort loadPort;
  private final CarLinkAddRequestMapper addRequestMapper;
  private final AddRequestValidator<CarLinkAddRequest> addValidator;
  private final UpdateRequestValidator<CarLinkUpdateRequest> updateValidator;
  private final QDateTime qDateTime;

  @Override
  public Long add(final CarLinkAddRequest request) {
    final var violationsCollector = addValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }

    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final CarLinkUpdateRequest request) {
    final var violationsCollector = updateValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    final var linkToClose = loadPort.loadById(request.getId());
    linkToClose.setDateEnd(getDateStart());
    updatePort.update(linkToClose);

    final var linkToCreate =
        CarLink.builder()
            .carId(request.getCarId())
            .dateStart(getDateEnd())
            .driverId(request.getDriverId())
            .comment(request.getComment())
            .build();

    addPort.add(linkToCreate);
  }

  @Override
  public void delete(final CarLinkDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  @Override
  public void close(final CarLinkCloseRequest request) {
    final var linkToClose = loadPort.loadById(request.getId());
    linkToClose.setDateEnd(getDateStart());
    updatePort.update(linkToClose);
  }

  private LocalDate getDateStart() {
    final var today = qDateTime.getToday();

    return today.minusDays(1L);
  }

  private LocalDate getDateEnd() {
    return qDateTime.getToday();
  }
}
