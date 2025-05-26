package ee.qrent.billing.driver.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetFirmLinkQuery;
import ee.qrent.billing.driver.api.in.request.FirmLinkResponse;
import ee.qrent.billing.driver.api.in.request.FirmLinkUpdateRequest;
import ee.qrent.billing.driver.api.out.FirmLinkLoadPort;
import ee.qrent.billing.driver.core.mapper.FirmLinkResponseMapper;
import ee.qrent.billing.driver.core.mapper.FirmLinkUpdateRequestMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLinkQueryService implements GetFirmLinkQuery {

  private final FirmLinkLoadPort loadPort;
  private final FirmLinkResponseMapper mapper;
  private final FirmLinkUpdateRequestMapper updateRequestMapper;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public List<FirmLinkResponse> getAll() {

    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public FirmLinkResponse getById(final Long id) {

    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {

    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public FirmLinkUpdateRequest getUpdateRequestById(final Long id) {

    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public FirmLinkResponse getOneByDriverIdAndRequiredDate(
      final Long driverId, final LocalDate requiredDate) {

    return mapper.toResponse(loadPort.loadOneByDriverIdAndRequiredDate(driverId, requiredDate));
  }

  @Override
  public FirmLinkResponse getActiveByDriverIdAndQWeekId(final Long driverId, final Long qWeekId) {
    final var qWeek = qWeekQuery.getById(qWeekId);

    return mapper.toResponse(loadPort.loadOneActiveByDriverIdAndDate(driverId, qWeek.getStart()));
  }
}
