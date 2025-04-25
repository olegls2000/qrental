package ee.qrent.billing.bonus.core.service;

import ee.qrent.billing.bonus.api.in.request.BonusProgramAddRequest;
import ee.qrent.billing.bonus.api.in.request.BonusProgramDeleteRequest;
import ee.qrent.billing.bonus.api.in.request.BonusProgramUpdateRequest;
import ee.qrent.billing.bonus.api.in.usecase.BonusProgramAddUseCase;
import ee.qrent.billing.bonus.api.in.usecase.BonusProgramDeleteUseCase;
import ee.qrent.billing.bonus.api.in.usecase.BonusProgramUpdateUseCase;
import ee.qrent.billing.bonus.api.out.BonusProgramAddPort;
import ee.qrent.billing.bonus.api.out.BonusProgramDeletePort;
import ee.qrent.billing.bonus.api.out.BonusProgramUpdatePort;
import ee.qrent.billing.bonus.core.mapper.BonusProgramAddRequestMapper;
import ee.qrent.billing.bonus.core.mapper.BonusProgramUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusProgramService
    implements BonusProgramAddUseCase, BonusProgramUpdateUseCase, BonusProgramDeleteUseCase {
  private final BonusProgramAddPort addPort;
  private final BonusProgramUpdatePort updatePort;
  private final BonusProgramDeletePort deletePort;
  private final BonusProgramAddRequestMapper addRequestMapper;
  private final BonusProgramUpdateRequestMapper updateRequestMapper;

  @Override
  public void add(final BonusProgramAddRequest request) {
    addPort.add(addRequestMapper.toDomain(request));
  }

  @Override
  public void update(final BonusProgramUpdateRequest request) {
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final BonusProgramDeleteRequest request) {
    deletePort.delete(request.getId());
  }
}
