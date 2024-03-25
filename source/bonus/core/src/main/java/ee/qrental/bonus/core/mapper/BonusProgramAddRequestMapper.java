package ee.qrental.bonus.core.mapper;

import ee.qrental.bonus.api.in.request.BonusProgramAddRequest;
import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.common.core.in.mapper.AddRequestMapper;
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
