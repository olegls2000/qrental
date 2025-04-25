package ee.qrent.billing.bonus.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.billing.bonus.api.in.request.BonusProgramAddRequest;
import ee.qrent.billing.bonus.domain.BonusProgram;

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
