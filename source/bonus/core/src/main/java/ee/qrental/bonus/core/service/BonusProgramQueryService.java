package ee.qrental.bonus.core.service;

import ee.qrental.bonus.api.in.query.GetBonusProgramQuery;
import ee.qrental.bonus.api.in.request.BonusProgramUpdateRequest;
import ee.qrental.bonus.api.in.response.BonusProgramResponse;
import ee.qrental.bonus.api.out.BonusProgramLoadPort;
import ee.qrental.bonus.core.mapper.BonusProgramResponseMapper;
import ee.qrental.bonus.core.mapper.BonusProgramUpdateRequestMapper;
import java.util.List;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class BonusProgramQueryService implements GetBonusProgramQuery {

  private final BonusProgramLoadPort loadPort;
  private final BonusProgramResponseMapper responseMapper;
  private final BonusProgramUpdateRequestMapper updateRequestMapper;

  @Override
  public List<BonusProgramResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).toList();
  }

  @Override
  public BonusProgramResponse getById(final Long id) {
    return responseMapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {
    final var domain = loadPort.loadById(id);

    return format("Bonus program, code: %s, name: %s", domain.getCode(), domain.getNameEng());
  }

  @Override
  public BonusProgramUpdateRequest getUpdateRequestById(Long id) {
    final var domain = loadPort.loadById(id);

    return updateRequestMapper.toRequest(domain);
  }
}
