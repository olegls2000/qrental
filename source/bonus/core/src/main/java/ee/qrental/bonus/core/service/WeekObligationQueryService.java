package ee.qrental.bonus.core.service;

import ee.qrental.bonus.api.in.query.GetWeekObligationQuery;
import ee.qrental.bonus.api.in.request.WeekObligationUpdateRequest;
import ee.qrental.bonus.api.in.response.WeekObligationResponse;
import ee.qrental.bonus.api.out.WeekObligationLoadPort;
import ee.qrental.bonus.core.mapper.WeekObligationResponseMapper;
import ee.qrental.bonus.core.mapper.WeekObligationUpdateRequestMapper;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;



@AllArgsConstructor
public class WeekObligationQueryService implements GetWeekObligationQuery {

  private final WeekObligationLoadPort loadPort;
  private final WeekObligationResponseMapper mapper;
  private final WeekObligationUpdateRequestMapper updateRequestMapper;

  @Override
  public List<WeekObligationResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public WeekObligationResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public WeekObligationUpdateRequest getUpdateRequestById(final Long id) {
    return null;
  }

}
