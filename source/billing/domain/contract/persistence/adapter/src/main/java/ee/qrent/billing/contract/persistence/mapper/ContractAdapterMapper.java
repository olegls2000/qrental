package ee.qrent.billing.contract.persistence.mapper;

import ee.qrent.billing.contract.domain.Contract;
import ee.qrent.billing.contract.domain.ContractDuration;
import ee.qrent.billing.contract.persistence.entity.jakarta.ContractJakartaEntity;
import lombok.AllArgsConstructor;

import static java.util.Arrays.asList;

@AllArgsConstructor
public class ContractAdapterMapper {

  public Contract mapToDomain(final ContractJakartaEntity entity) {
    if (entity == null) {

      return null;
    }

    return Contract.builder()
        .id(entity.getId())
        .number(entity.getNumber())
        .contractDuration(ContractDuration.valueOf(entity.getContractDuration()))
        .renterName(entity.getRenterName())
        .renterLhvAccount(entity.getRenterLhvAccount())
        .renterRegistrationNumber(entity.getRenterRegistrationNumber())
        .renterAddress(entity.getRenterAddress())
        .renterCeoName(entity.getRenterCeoName())
        .renterCeoIsikukood(entity.getRenterCeoIsikukood())
        .renterPhone(entity.getRenterPhone())
        .renterEmail(entity.getRenterEmail())
        .driverId(entity.getDriverId())
        .driverIsikukood(entity.getDriverIsikukood())
        .driverLicenceNumber(entity.getDriverLicenceNumber())
        .driverAddress(entity.getDriverAddress())
        .qFirmId(entity.getQFirmId())
        .qFirmName(entity.getQFirmName())
        .qFirmRegistrationNumber(entity.getQFirmRegistrationNumber())
        .qFirmVatNumber(entity.getQFirmVatNumber())
        .qFirmIban(entity.getQFirmIban())
        .qFirmPostAddress(entity.getQFirmPostAddress())
        .qFirmEmail(entity.getQFirmEmail())
        .qFirmVatPhone(entity.getQFirmVatPhone())
        .qFirmCeo(entity.getQFirmCeo())
        .qFirmCeoDeputies(
            asList(
                entity.getQFirmCeoDeputy1(),
                entity.getQFirmCeoDeputy2(),
                entity.getQFirmCeoDeputy3()))
        .carVin(entity.getCarVin())
        .carManufacturer(entity.getCarManufacturer())
        .carModel(entity.getCarModel())
        .created(entity.getCreated())
        .dateStart(entity.getDateStart())
        .dateEnd(entity.getDateEnd())
        .build();
  }

  public ContractJakartaEntity mapToEntity(final Contract domain) {

    return ContractJakartaEntity.builder()
        .id(domain.getId())
        .number(domain.getNumber())
        .contractDuration(domain.getContractDuration().name())
        .renterName(domain.getRenterName())
        .renterLhvAccount(domain.getRenterLhvAccount())
        .renterRegistrationNumber(domain.getRenterRegistrationNumber())
        .renterAddress(domain.getRenterAddress())
        .renterCeoName(domain.getRenterCeoName())
        .renterCeoIsikukood(domain.getRenterCeoIsikukood())
        .renterPhone(domain.getRenterPhone())
        .renterEmail(domain.getRenterEmail())
        .driverId(domain.getDriverId())
        .driverIsikukood(domain.getDriverIsikukood())
        .driverLicenceNumber(domain.getDriverLicenceNumber())
        .driverAddress(domain.getDriverAddress())
        .qFirmId(domain.getQFirmId())
        .qFirmName(domain.getQFirmName())
        .qFirmRegistrationNumber(domain.getQFirmRegistrationNumber())
        .qFirmVatNumber(domain.getQFirmVatNumber())
        .qFirmIban(domain.getQFirmIban())
        .qFirmPostAddress(domain.getQFirmPostAddress())
        .qFirmEmail(domain.getQFirmEmail())
        .qFirmVatPhone(domain.getQFirmVatPhone())
        .qFirmCeo(domain.getQFirmCeo())
        .qFirmCeoDeputy1(getQfirmCeoDeputy1(domain))
        .qFirmCeoDeputy2(getQfirmCeoDeputy2(domain))
        .qFirmCeoDeputy3(getQfirmCeoDeputy3(domain))
        .carVin(domain.getCarVin())
        .carManufacturer(domain.getCarManufacturer())
        .carModel(domain.getCarModel())
        .created(domain.getCreated())
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .build();
  }

  private String getQfirmCeoDeputy1(final Contract contract) {
    final var qFirmCeoDeputies = contract.getQFirmCeoDeputies();
    if (qFirmCeoDeputies != null && qFirmCeoDeputies.size() > 0) {

      return qFirmCeoDeputies.get(0);
    }
    return null;
  }

  private String getQfirmCeoDeputy2(final Contract contract) {
    final var qFirmCeoDeputies = contract.getQFirmCeoDeputies();
    if (qFirmCeoDeputies != null && qFirmCeoDeputies.size() > 1) {

      return qFirmCeoDeputies.get(1);
    }

    return null;
  }

  private String getQfirmCeoDeputy3(final Contract contract) {
    final var qFirmCeoDeputies = contract.getQFirmCeoDeputies();
    if (qFirmCeoDeputies != null && qFirmCeoDeputies.size() > 2) {

      return qFirmCeoDeputies.get(2);
    }

    return null;
  }
}
