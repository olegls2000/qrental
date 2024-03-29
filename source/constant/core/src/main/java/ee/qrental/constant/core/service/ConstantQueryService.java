package ee.qrental.constant.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.constant.api.in.query.GetConstantQuery;
import ee.qrental.constant.api.in.request.ConstantUpdateRequest;
import ee.qrental.constant.api.in.response.ConstantResponse;
import ee.qrental.constant.api.out.ConstantLoadPort;
import ee.qrental.constant.core.mapper.ConstantResponseMapper;
import ee.qrental.constant.core.mapper.ConstantUpdateRequestMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConstantQueryService implements GetConstantQuery {

  private final ConstantLoadPort loadPort;
  private final ConstantResponseMapper mapper;
  private final ConstantUpdateRequestMapper updateRequestMapper;

  @Override
  public List<ConstantResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public ConstantResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public ConstantUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public ConstantResponse getByName(final String name) {
    return mapper.toResponse(loadPort.loadByName(name));
  }
}
