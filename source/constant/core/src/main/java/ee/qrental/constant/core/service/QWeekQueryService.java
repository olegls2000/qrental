package ee.qrental.constant.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.constant.api.in.query.qweek.GetQWeekQuery;
import ee.qrental.constant.api.in.request.qweek.QWeekUpdateRequest;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import ee.qrental.constant.api.out.qweek.QWeekLoadPort;
import ee.qrental.constant.core.mapper.QWeekResponseMapper;
import ee.qrental.constant.core.mapper.QWeekUpdateRequestMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QWeekQueryService implements GetQWeekQuery {

  private final QWeekLoadPort loadPort;
  private final QWeekResponseMapper mapper;
  private final QWeekUpdateRequestMapper updateRequestMapper;

  @Override
  public List<QWeekResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public QWeekResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public QWeekUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }
}
