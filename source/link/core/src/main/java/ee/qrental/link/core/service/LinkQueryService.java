package ee.qrental.link.core.service;

import ee.qrental.link.api.in.query.GetLinkQuery;
import ee.qrental.link.api.in.request.LinkUpdateRequest;
import ee.qrental.link.api.in.response.LinkResponse;
import ee.qrental.link.api.out.LinkLoadPort;
import ee.qrental.link.core.mapper.LinkResponseMapper;
import ee.qrental.link.core.mapper.LinkUpdateRequestMapper;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;



@AllArgsConstructor
public class LinkQueryService implements GetLinkQuery {

  private final LinkLoadPort loadPort;
  private final LinkResponseMapper mapper;
  private final LinkUpdateRequestMapper updateRequestMapper;

  @Override
  public List<LinkResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public LinkResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public LinkUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }
}
