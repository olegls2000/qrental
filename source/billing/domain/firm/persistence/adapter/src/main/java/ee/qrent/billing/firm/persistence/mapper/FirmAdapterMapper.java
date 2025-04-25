package ee.qrent.billing.firm.persistence.mapper;

import ee.qrent.billing.firm.domain.Firm;
import ee.qrent.billing.firm.persistence.entity.jakarta.FirmJakartaEntity;

public class FirmAdapterMapper {

  public Firm mapToDomain(final FirmJakartaEntity entity) {
    return Firm.builder()
        .id(entity.getId())
        .name(entity.getName())
        .iban(entity.getIban())
        .ceoFirstName(entity.getCeoFirstName())
        .ceoLastName(entity.getCeoLastName())
        .ceoDeputy1FirstName(entity.getCeoDeputy1FirstName())
        .ceoDeputy1LastName(entity.getCeoDeputy1LastName())
        .ceoDeputy2FirstName(entity.getCeoDeputy2FirstName())
        .ceoDeputy2LastName(entity.getCeoDeputy2LastName())
        .ceoDeputy3FirstName(entity.getCeoDeputy3FirstName())
        .ceoDeputy3LastName(entity.getCeoDeputy3LastName())
        .registrationNumber(entity.getRegistrationNumber())
        .vatNumber(entity.getVatNumber())
        .email(entity.getEmail())
        .postAddress(entity.getPostAddress())
        .phone(entity.getPhone())
        .bank(entity.getBank())
        .qGroup(entity.getQGroup())
        .comment(entity.getComment())
        .build();
  }

  public FirmJakartaEntity mapToEntity(final Firm domain) {
    return FirmJakartaEntity.builder()
        .id(domain.getId())
        .name(domain.getName())
        .iban(domain.getIban())
        .ceoFirstName(domain.getCeoFirstName())
        .ceoLastName(domain.getCeoLastName())
        .ceoDeputy1FirstName(domain.getCeoDeputy1FirstName())
        .ceoDeputy1LastName(domain.getCeoDeputy1LastName())
        .ceoDeputy2FirstName(domain.getCeoDeputy2FirstName())
        .ceoDeputy2LastName(domain.getCeoDeputy2LastName())
        .ceoDeputy3FirstName(domain.getCeoDeputy3FirstName())
        .ceoDeputy3LastName(domain.getCeoDeputy3LastName())
        .registrationNumber(domain.getRegistrationNumber())
        .vatNumber(domain.getVatNumber())
        .email(domain.getEmail())
        .postAddress(domain.getPostAddress())
        .phone(domain.getPhone())
        .bank(domain.getBank())
        .qGroup(domain.getQGroup())
        .comment(domain.getComment())
        .build();
  }
}
