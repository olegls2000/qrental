package ee.qrental.bonus.core.mapper;

import ee.qrental.bonus.api.in.request.BonusProgramUpdateRequest;
import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusProgramUpdateRequestMapper
    implements UpdateRequestMapper<BonusProgramUpdateRequest, BonusProgram> {

  @Override
  public BonusProgram toDomain(final BonusProgramUpdateRequest request) {
    return BonusProgram.builder()
        .id(request.getId())
        .active(request.getActive())
        .code(request.getCode())
        .nameEng(request.getNameEng())
        .nameRus(request.getNameRus())
        .nameEst(request.getNameEst())
        .description(request.getDescription())
        .build();
  }

  @Override
  public BonusProgramUpdateRequest toRequest(final BonusProgram domain) {
    return BonusProgramUpdateRequest.builder()
        .id(domain.getId())
        .code(domain.getCode())
        .active(domain.getActive())
        .nameEng(domain.getNameEng())
        .nameRus(domain.getNameRus())
        .nameEst(domain.getNameEst())
        .description(domain.getDescription())
        .build();
  }
}
