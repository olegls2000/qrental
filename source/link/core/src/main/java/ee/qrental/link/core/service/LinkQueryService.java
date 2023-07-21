package ee.qrental.link.core.service;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import ee.qrental.link.api.in.query.GetLinkQuery;
import ee.qrental.link.api.in.request.LinkUpdateRequest;
import ee.qrental.link.api.in.response.LinkResponse;
import ee.qrental.link.api.out.LinkLoadPort;
import ee.qrental.link.core.mapper.LinkResponseMapper;
import ee.qrental.link.core.mapper.LinkUpdateRequestMapper;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

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
    final var domain = loadPort.loadById(id);
    if (domain == null) {
      System.out.println(format("Link with id = %d was not found", id));
      return null;
    }
    return mapper.toResponse(domain);
  }

  @Override
  public String getObjectInfo(Long id) {
    final var domain = loadPort.loadById(id);
    if (domain == null) {
      System.out.println(format("Link with id = %d was not found", id));
      return null;
    }
    return mapper.toObjectInfo(domain);
  }

  @Override
  public LinkUpdateRequest getUpdateRequestById(Long id) {
    final var domain = loadPort.loadById(id);
    if (domain == null) {
      System.out.println(format("Link with id = %d was not found", id));
      return null;
    }
    return updateRequestMapper.toRequest(domain);
  }

  @Override
  public LinkResponse getActiveLinkByDriverId(final Long driverId) {
    final var domain = loadPort.loadActiveByDriverId(driverId);
    if (domain == null) {
      System.out.println(format("Active Link for driver with id = %d was not found", driverId));
      return null;
    }
    return mapper.toResponse(domain);
  }
}
