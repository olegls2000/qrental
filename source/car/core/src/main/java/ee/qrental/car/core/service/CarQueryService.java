package ee.qrental.car.core.service;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.in.request.CarUpdateRequest;
import ee.qrental.car.api.in.response.CarResponse;
import ee.qrental.car.api.out.CarLoadPort;
import ee.qrental.car.core.mapper.CarResponseMapper;
import ee.qrental.car.core.mapper.CarUpdateRequestMapper;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class CarQueryService implements GetCarQuery {

  private final CarLoadPort loadPort;
  private final CarResponseMapper mapper;
  private final CarUpdateRequestMapper updateRequestMapper;

  @Override
  public List<CarResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public CarResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public CarUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }
}
