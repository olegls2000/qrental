package ee.qrental.constant.core.service;

import ee.qrental.constant.api.in.request.QWeekAddRequest;
import ee.qrental.constant.api.in.request.QWeekDeleteRequest;
import ee.qrental.constant.api.in.request.QWeekUpdateRequest;
import ee.qrental.constant.api.in.usecase.QWeekAddUseCase;
import ee.qrental.constant.api.in.usecase.QWeekDeleteUseCase;
import ee.qrental.constant.api.in.usecase.QWeekUpdateUseCase;
import ee.qrental.constant.api.out.QWeekAddPort;
import ee.qrental.constant.api.out.QWeekDeletePort;
import ee.qrental.constant.api.out.QWeekLoadPort;
import ee.qrental.constant.api.out.QWeekUpdatePort;
import ee.qrental.constant.core.mapper.QWeekAddRequestMapper;
import ee.qrental.constant.core.mapper.QWeekUpdateRequestMapper;
import ee.qrental.constant.core.validator.QWeekAddBusinessRuleValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QWeekUseCaseService
    implements QWeekAddUseCase, QWeekUpdateUseCase, QWeekDeleteUseCase {

  private final QWeekAddPort addPort;
  private final QWeekUpdatePort updatePort;
  private final QWeekDeletePort deletePort;
  private final QWeekLoadPort loadPort;
  private final QWeekAddRequestMapper addRequestMapper;
  private final QWeekUpdateRequestMapper updateRequestMapper;
  private final QWeekAddBusinessRuleValidator addBusinessRuleValidator;

  @Override
  public Long add(final QWeekAddRequest request) {
    final var violationsCollector = addBusinessRuleValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final QWeekUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final QWeekDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Q Week failed. No Record with id = " + id);
    }
  }
}
