package ee.qrent.billing.driver.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.driver.api.in.request.CallSignLinkAddRequest;
import ee.qrent.billing.driver.api.in.request.CallSignLinkCloseRequest;
import ee.qrent.billing.driver.api.in.request.CallSignLinkDeleteRequest;
import ee.qrent.billing.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrent.billing.driver.api.in.usecase.CallSignLinkAddUseCase;
import ee.qrent.billing.driver.api.in.usecase.CallSignLinkCloseUseCase;
import ee.qrent.billing.driver.api.in.usecase.CallSignLinkDeleteUseCase;
import ee.qrent.billing.driver.api.in.usecase.CallSignLinkUpdateUseCase;
import ee.qrent.billing.driver.api.out.CallSignLinkAddPort;
import ee.qrent.billing.driver.api.out.CallSignLinkDeletePort;
import ee.qrent.billing.driver.api.out.CallSignLinkLoadPort;
import ee.qrent.billing.driver.api.out.CallSignLinkUpdatePort;
import ee.qrent.billing.driver.core.mapper.CallSignLinkAddRequestMapper;
import ee.qrent.billing.driver.core.mapper.CallSignLinkUpdateRequestMapper;
import ee.qrent.billing.driver.core.validator.CallSignLinkRequestValidator;
import ee.qrent.billing.driver.domain.CallSign;
import ee.qrent.billing.driver.domain.CallSignLink;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkUseCaseService
    implements CallSignLinkAddUseCase,
        CallSignLinkUpdateUseCase,
        CallSignLinkCloseUseCase,
        CallSignLinkDeleteUseCase {

  private final CallSignLinkAddPort addPort;
  private final CallSignLinkUpdatePort updatePort;
  private final CallSignLinkDeletePort deletePort;
  private final CallSignLinkLoadPort loadPort;
  private final CallSignLinkAddRequestMapper addRequestMapper;
  private final CallSignLinkUpdateRequestMapper updateRequestMapper;
  private final CallSignLinkRequestValidator requestValidator;
  private final QDateTime qDateTime;

  @Override
  public Long add(final CallSignLinkAddRequest request) {
    final var violationsCollector = requestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return null;
    }

    final var domain = addRequestMapper.toDomain(request);
    return addPort.add(domain).getId();
  }

  @Override
  public void update(final CallSignLinkUpdateRequest request) {
    final var violationsCollector = requestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    final var linkToClose = loadPort.loadById(request.getId());
    final var today = qDateTime.getToday();

    linkToClose.setDateEnd(today.minusDays(1L));
    updatePort.update(linkToClose);

    final var linkToCreate =
        CallSignLink.builder()
            .callSign(CallSign.builder().id(request.getCallSignId()).build())
            .dateStart(today)
            .driverId(request.getDriverId())
            .build();

    addPort.add(linkToCreate);
  }

  @Override
  public void delete(final CallSignLinkDeleteRequest request) {



    deletePort.delete(request.getId());
  }

  @Override
  public void close(final CallSignLinkCloseRequest request) {
    final var linkToClose = loadPort.loadById(request.getId());
    final var today = qDateTime.getToday();
    linkToClose.setDateEnd(today.minusDays(1L));
    updatePort.update(linkToClose);
  }
}
