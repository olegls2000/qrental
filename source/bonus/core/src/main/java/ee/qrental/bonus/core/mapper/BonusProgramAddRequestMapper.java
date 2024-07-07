package ee.qrental.bonus.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.bonus.api.in.request.BonusProgramAddRequest;
import ee.qrental.bonus.domain.BonusProgram;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusProgramAddRequestMapper
    implements AddRequestMapper<BonusProgramAddRequest, BonusProgram> {

  @Override
  public BonusProgram toDomain(final BonusProgramAddRequest request) {

    return BonusProgram.builder()
        .code(request.getCode())
        .active(request.getActive())
        .nameEng(request.getNameEng())
        .nameRus(request.getNameRus())
        .nameEst(request.getNameEst())
        .description(request.getDescription())
        .build();
  }
}
