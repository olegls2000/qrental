package ee.qrental.bonus.core.service;

import ee.qrental.bonus.api.in.request.BonusProgramAddRequest;
import ee.qrental.bonus.api.in.request.BonusProgramDeleteRequest;
import ee.qrental.bonus.api.in.request.BonusProgramUpdateRequest;
import ee.qrental.bonus.api.in.usecase.BonusProgramAddUseCase;
import ee.qrental.bonus.api.in.usecase.BonusProgramDeleteUseCase;
import ee.qrental.bonus.api.in.usecase.BonusProgramUpdateUseCase;
import ee.qrental.bonus.api.out.BonusProgramAddPort;
import ee.qrental.bonus.api.out.BonusProgramDeletePort;
import ee.qrental.bonus.api.out.BonusProgramUpdatePort;
import ee.qrental.bonus.core.mapper.BonusProgramAddRequestMapper;
import ee.qrental.bonus.core.mapper.BonusProgramUpdateRequestMapper;
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
