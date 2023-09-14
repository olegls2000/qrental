package ee.qrental.contract.adapter.mapper;


import static java.util.Arrays.asList;

import ee.qrental.contract.domain.Contract;
import ee.qrental.contract.entity.jakarta.ContractJakartaEntity;

public class ContractAdapterMapper {

  public Contract mapToDomain(final ContractJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return Contract.builder()
        .id(entity.getId())
        .number(entity.getNumber())
        .renterName(entity.getRenterName())
        .renterRegistrationNumber(entity.getRenterRegistrationNumber())
        .renterCeoName(entity.getRenterCeoName())
        .renterCeoIsikukood(entity.getRenterCeoIsikukood())
        .renterPhone(entity.getRenterPhone())
        .renterEmail(entity.getRenterEmail())
        .driverId(entity.getDriverId())
        .driverIsikukood(entity.getDriverIsikukood())
        .driverLicenceNumber(entity.getDriverLicenceNumber())
        .qFirmId(entity.getQFirmId())
        .qFirmName(entity.getQFirmName())
        .qFirmRegistrationNumber(entity.getQFirmRegistrationNumber())
        .qFirmPostAddress(entity.getQFirmPostAddress())
        .qFirmEmail(entity.getQFirmEmail())
        .qFirmCeo(entity.getRenterCeoName())
         .qFirmCeoDeputies(asList(entity.getQFirmCeoDeputy1(), entity.getQFirmCeoDeputy2(), entity.getQFirmCeoDeputy3()))
         .created(entity.getCreated())
        .build();
  }

  public ContractJakartaEntity mapToEntity(final Contract domain) {

    return ContractJakartaEntity.builder()
            .id(domain.getId())
            .number(domain.getNumber())
            .renterName(domain.getRenterName())
            .renterRegistrationNumber(domain.getRenterRegistrationNumber())
            .renterCeoName(domain.getRenterCeoName())
            .renterCeoIsikukood(domain.getRenterCeoIsikukood())
            .renterPhone(domain.getRenterPhone())
            .renterEmail(domain.getRenterEmail())
            .driverId(domain.getDriverId())
            .driverIsikukood(domain.getDriverIsikukood())
            .driverLicenceNumber(domain.getDriverLicenceNumber())
            .qFirmId(domain.getQFirmId())
            .qFirmName(domain.getQFirmName())
            .qFirmRegistrationNumber(domain.getQFirmRegistrationNumber())
            .qFirmPostAddress(domain.getQFirmPostAddress())
            .qFirmEmail(domain.getQFirmEmail())
            .qFirmCeo(domain.getRenterCeoName())
            .qFirmCeoDeputy1(getQfirmCeoDeputy1(domain))
            .qFirmCeoDeputy2(getQfirmCeoDeputy2(domain))
            .qFirmCeoDeputy3(getQfirmCeoDeputy3(domain))
            .created(domain.getCreated())
        .build();
  }
  
  private String getQfirmCeoDeputy1(final Contract contract){
    final var qFirmCeoDeputies = contract.getQFirmCeoDeputies();
    if(qFirmCeoDeputies!= null && qFirmCeoDeputies.size()>0) {
      return qFirmCeoDeputies.get(0);
    }
    return null;
  }

  private String getQfirmCeoDeputy2(final Contract contract){
    final var qFirmCeoDeputies = contract.getQFirmCeoDeputies();
    if(qFirmCeoDeputies!= null && qFirmCeoDeputies.size()>1) {
      return qFirmCeoDeputies.get(1);
    }

    return null;
  }

  private String getQfirmCeoDeputy3(final Contract contract){
    final var qFirmCeoDeputies = contract.getQFirmCeoDeputies();
    if(qFirmCeoDeputies!= null && qFirmCeoDeputies.size()>2) {
      return qFirmCeoDeputies.get(2);
    }
    return null;
  }
}