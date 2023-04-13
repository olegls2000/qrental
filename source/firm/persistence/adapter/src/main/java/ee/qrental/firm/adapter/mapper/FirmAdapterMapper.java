package ee.qrental.firm.adapter.mapper;

import ee.qrental.firm.domain.Firm;
import ee.qrental.firm.entity.jakarta.FirmJakartaEntity;

public class FirmAdapterMapper {

  public Firm mapToDomain(final FirmJakartaEntity entity) {
    return Firm.builder()
        .id(entity.getId())
            .firmName(entity.getFirmName())
            .iban(entity.getIban())
            .regNumber(entity.getRegNumber())
            .vatNumber(entity.getVatNumber())
            .comment(entity.getComment())
            .email(entity.getEmail())
            .postAddress(entity.getPostAddress())
            .phone(entity.getPhone())
            .bank(entity.getBank())
            .qGroup(entity.getQGroup())
            .build();
  }

  public FirmJakartaEntity mapToEntity(final Firm domain) {
    return FirmJakartaEntity.builder()
        .id(domain.getId())
            .firmName(domain.getFirmName())
            .iban(domain.getIban())
            .regNumber(domain.getRegNumber())
            .vatNumber(domain.getVatNumber())
            .comment(domain.getComment())
            .email(domain.getEmail())
            .postAddress(domain.getPostAddress())
            .phone(domain.getPhone())
            .bank(domain.getBank())
            .qGroup(domain.getQGroup())
            .build();
  }
}
