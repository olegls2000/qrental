package ee.qrental.callsign.core.service;

import ee.qrental.callsign.api.in.request.CallSignAddRequest;
import ee.qrental.callsign.api.in.request.CallSignDeleteRequest;
import ee.qrental.callsign.api.in.request.CallSignUpdateRequest;
import ee.qrental.callsign.api.in.usecase.CallSignAddUseCase;
import ee.qrental.callsign.api.in.usecase.CallSignDeleteUseCase;
import ee.qrental.callsign.api.in.usecase.CallSignUpdateUseCase;
import ee.qrental.callsign.api.out.CallSignAddPort;
import ee.qrental.callsign.api.out.CallSignDeletePort;
import ee.qrental.callsign.api.out.CallSignLoadPort;
import ee.qrental.callsign.api.out.CallSignUpdatePort;
import ee.qrental.callsign.core.mapper.CallSignAddRequestMapper;
import ee.qrental.callsign.core.mapper.CallSignUpdateRequestMapper;
import ee.qrental.callsign.core.validator.CallSignBusinessRuleValidator;import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignUseCaseService
    implements CallSignAddUseCase, CallSignUpdateUseCase, CallSignDeleteUseCase {

  private final CallSignAddPort addPort;
  private final CallSignUpdatePort updatePort;
  private final CallSignDeletePort deletePort;
  private final CallSignLoadPort loadPort;
  private final CallSignAddRequestMapper addRequestMapper;
  private final CallSignUpdateRequestMapper updateRequestMapper;
  private final CallSignBusinessRuleValidator businessRuleValidator;

  @Override
  public Long add(final CallSignAddRequest request) {
    final var domain = addRequestMapper.toDomain(request);
    final var violationsCollector = businessRuleValidator.validateAdd(domain);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return null;
    }

    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final CallSignUpdateRequest request) {
    checkExistence(request.getId());
    final var domain = updateRequestMapper.toDomain(request);
    final var violationsCollector = businessRuleValidator.validateUpdate(domain);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final CallSignDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of CallSign failed. No Record with id = " + id);
    }
  }
}
