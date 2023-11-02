package ee.qrental.constant.core.service;

import ee.qrental.constant.api.in.request.qweek.QWeekAddRequest;
import ee.qrental.constant.api.in.request.qweek.QWeekDeleteRequest;
import ee.qrental.constant.api.in.request.qweek.QWeekUpdateRequest;
import ee.qrental.constant.api.in.usecase.qweek.QWeekAddUseCase;
import ee.qrental.constant.api.in.usecase.qweek.QWeekDeleteUseCase;
import ee.qrental.constant.api.in.usecase.qweek.QWeekUpdateUseCase;
import ee.qrental.constant.api.out.qweek.QWeekAddPort;
import ee.qrental.constant.api.out.qweek.QWeekDeletePort;
import ee.qrental.constant.api.out.qweek.QWeekLoadPort;
import ee.qrental.constant.api.out.qweek.QWeekUpdatePort;
import ee.qrental.constant.core.mapper.QWeekAddRequestMapper;
import ee.qrental.constant.core.mapper.QWeekUpdateRequestMapper;
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

  @Override
  public Long add(final QWeekAddRequest request) {
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
