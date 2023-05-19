package ee.qrental.driver.core.service;

import ee.qrental.driver.api.in.request.CallSignLinkAddRequest;
import ee.qrental.driver.api.in.request.CallSignLinkDeleteRequest;
import ee.qrental.driver.api.in.request.CallSignLinkUpdateRequest;
import ee.qrental.driver.api.in.usecase.CallSignLinkAddUseCase;
import ee.qrental.driver.api.in.usecase.CallSignLinkDeleteUseCase;
import ee.qrental.driver.api.in.usecase.CallSignLinkUpdateUseCase;
import ee.qrental.driver.api.out.CallSignLinkAddPort;
import ee.qrental.driver.api.out.CallSignLinkDeletePort;
import ee.qrental.driver.api.out.CallSignLinkLoadPort;
import ee.qrental.driver.api.out.CallSignLinkUpdatePort;
import ee.qrental.driver.core.mapper.CallSignLinkAddRequestMapper;
import ee.qrental.driver.core.mapper.CallSignLinkUpdateRequestMapper;
import ee.qrental.driver.core.validator.CallSignLinkBusinessRuleValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignLinkUseCaseService
    implements CallSignLinkAddUseCase, CallSignLinkUpdateUseCase, CallSignLinkDeleteUseCase {

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
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final CallSignLinkDeleteRequest request) {
    deletePort.delete(request.getId());
  }
}
