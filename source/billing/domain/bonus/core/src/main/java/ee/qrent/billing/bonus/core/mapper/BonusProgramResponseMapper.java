package ee.qrent.billing.bonus.core.mapper;

import ee.qrent.billing.bonus.api.in.response.BonusProgramResponse;
import ee.qrent.billing.bonus.domain.BonusProgram;
import ee.qrent.common.in.mapper.ResponseMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusProgramResponseMapper
    implements ResponseMapper<BonusProgramResponse, BonusProgram> {

  @Override
  public BonusProgramResponse toResponse(final BonusProgram domain) {
    if (domain == null) {
      return null;
    }

    return BonusProgramResponse.builder()
        .id(domain.getId())
        .code(domain.getCode())
        .nameEng(domain.getNameEng())
        .nameEst(domain.getNameEst())
        .nameRus(domain.getNameRus())
        .description(domain.getDescription())
        .active(domain.getActive())
        .build();
  }

  @Override
  public String toObjectInfo(final BonusProgram domain) {
    return null;
  }
}
