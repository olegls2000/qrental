package ee.qrental.bonus.adapter.mapper;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.entity.jakarta.BonusProgramJakartaEntity;

public class BonusProgramAdapterMapper {

  public BonusProgram mapToDomain(final BonusProgramJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return BonusProgram.builder()
        .id(entity.getId())
        .code(entity.getCode())
        .nameEng(entity.getNameEng())
        .nameRus(entity.getNameRus())
        .nameEst(entity.getNameEst())
        .description(entity.getDescription())
        .active(entity.getActive())
        .build();
  }

  public BonusProgramJakartaEntity mapToEntity(final BonusProgram domain) {
    return BonusProgramJakartaEntity.builder()
        .id(domain.getId())
        .code(domain.getCode())
        .nameEng(domain.getNameEng())
        .nameRus(domain.getNameRus())
        .nameEst(domain.getNameEst())
        .description(domain.getDescription())
        .active(domain.getActive())
        .build();
  }
}
