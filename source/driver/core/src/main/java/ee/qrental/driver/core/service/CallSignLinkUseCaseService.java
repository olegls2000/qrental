package ee.qrental.driver.core.service;

import ee.qrental.common.core.in.usecase.DeleteUseCase;
import ee.qrental.driver.api.in.request.CallSignLinkAddRequest;
import ee.qrental.driver.api.in.request.CallSignLinkDeleteRequest;
import ee.qrental.driver.api.in.request.CallSignLinkStopRequest;
import ee.qrental.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.driver.api.in.usecase.CallSignLinkAddUseCase;
import ee.qrental.driver.api.in.usecase.CallSignLinkStopUseCase;
import ee.qrental.driver.api.in.usecase.CallSignLinkUpdateUseCase;
import ee.qrental.driver.api.out.CallSignLinkAddPort;
import ee.qrental.driver.api.out.CallSignLinkDeletePort;
import ee.qrental.driver.api.out.CallSignLinkLoadPort;
import ee.qrental.driver.api.out.CallSignLinkUpdatePort;
import ee.qrental.driver.core.mapper.CallSignLinkAddRequestMapper;
import ee.qrental.driver.core.mapper.CallSignLinkUpdateRequestMapper;
import ee.qrental.driver.core.validator.CallSignLinkBusinessRuleValidator;
import ee.qrental.driver.domain.CallSign;
import ee.qrental.driver.domain.CallSignLink;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class CallSignLinkUseCaseService
    implements CallSignLinkAddUseCase,
        CallSignLinkUpdateUseCase,
        CallSignLinkStopUseCase, DeleteUseCase<CallSignLinkDeleteRequest> {

  private final CallSignLinkAddPort addPort;
  private final CallSignLinkUpdatePort updatePort;
  private final CallSignLinkDeletePort deletePort;
  private final CallSignLinkLoadPort loadPort;
  private final CallSignLinkAddRequestMapper addRequestMapper;
  private final CallSignLinkUpdateRequestMapper updateRequestMapper;
  private final CallSignLinkBusinessRuleValidator businessRuleValidator;

  @Override
  public Long add(final CallSignLinkAddRequest request) {
    final var domain = addRequestMapper.toDomain(request);
    final var violationsCollector = businessRuleValidator.validateAdd(domain);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return null;
    }

    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final CallSignLinkUpdateRequest request) {
    final var domain = updateRequestMapper.toDomain(request);
    final var violationsCollector = businessRuleValidator.validateUpdate(domain);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    final var linkToStop = loadPort.loadById(request.getId());
    linkToStop.setDateEnd(LocalDate.now().minusDays(1L));
    updatePort.update(linkToStop);

    final var linkToCreate =
        CallSignLink.builder()
            .callSign(CallSign.builder().id(request.getCallSignId()).build())
            .dateStart(LocalDate.now())
            .driverId(request.getDriverId())
            .build();

    addPort.add(linkToCreate);
  }

  @Override
  public void delete(final CallSignLinkDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  @Override
  public void stop(final CallSignLinkStopRequest request) {
    final var linkToStop = loadPort.loadById(request.getId());
    linkToStop.setDateEnd(LocalDate.now().minusDays(1L));
    updatePort.update(linkToStop);
  }
}
