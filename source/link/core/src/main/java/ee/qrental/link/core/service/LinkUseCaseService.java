package ee.qrental.link.core.service;

import ee.qrental.link.api.in.request.LinkAddRequest;
import ee.qrental.link.api.in.request.LinkDeleteRequest;
import ee.qrental.link.api.in.request.LinkUpdateRequest;
import ee.qrental.link.api.in.usecase.LinkAddUseCase;
import ee.qrental.link.api.in.usecase.LinkDeleteUseCase;
import ee.qrental.link.api.in.usecase.LinkUpdateUseCase;
import ee.qrental.link.api.out.LinkAddPort;
import ee.qrental.link.api.out.LinkDeletePort;
import ee.qrental.link.api.out.LinkLoadPort;
import ee.qrental.link.api.out.LinkUpdatePort;
import ee.qrental.link.core.mapper.LinkAddRequestMapper;
import ee.qrental.link.core.mapper.LinkUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LinkUseCaseService implements LinkAddUseCase, LinkUpdateUseCase, LinkDeleteUseCase {

  private final LinkAddPort addPort;
  private final LinkUpdatePort updatePort;
  private final LinkDeletePort deletePort;
  private final LinkLoadPort loadPort;
  private final LinkAddRequestMapper addRequestMapper;
  private final LinkUpdateRequestMapper updateRequestMapper;

  @Override
  public Long add(final LinkAddRequest request) {
    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final LinkUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final LinkDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Link failed. No Record with id = " + id);
    }
  }
}
