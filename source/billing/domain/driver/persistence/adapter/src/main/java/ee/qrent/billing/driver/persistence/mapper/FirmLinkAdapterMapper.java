package ee.qrent.billing.driver.persistence.mapper;

//import common.out.mapper.DomainMapper;
import ee.qrent.common.out.mapper.DomainMapper;
import ee.qrent.billing.driver.domain.FirmLink;
import ee.qrent.billing.driver.persistence.entity.jakarta.FirmLinkJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FirmLinkAdapterMapper
    implements DomainMapper<FirmLink, FirmLinkJakartaEntity> {

  @Override
  public FirmLink mapToDomain(final FirmLinkJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return FirmLink.builder()
        .id(entity.getId())
        .firmId(entity.getFirmId())
        .driverId(entity.getDriver().getId())
        .dateStart(entity.getDateStart())
        .dateEnd(entity.getDateEnd())
        .comment(entity.getComment())
        .build();
  }

  @Override
  public FirmLinkJakartaEntity mapToEntity(final FirmLink domain) {

    return FirmLinkJakartaEntity.builder()
        .id(domain.getId())
        .firmId(domain.getFirmId())
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .comment(domain.getComment())
        .build();
  }
}
