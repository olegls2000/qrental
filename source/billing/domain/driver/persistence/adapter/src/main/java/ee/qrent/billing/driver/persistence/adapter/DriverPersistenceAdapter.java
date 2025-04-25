package ee.qrent.billing.driver.persistence.adapter;

import ee.qrent.billing.driver.persistence.mapper.DriverAdapterMapper;
import ee.qrent.billing.driver.persistence.repository.DriverRepository;
import ee.qrent.billing.driver.api.out.DriverAddPort;
import ee.qrent.billing.driver.api.out.DriverDeletePort;
import ee.qrent.billing.driver.api.out.DriverUpdatePort;
import ee.qrent.billing.driver.domain.Driver;
import ee.qrent.billing.driver.persistence.entity.jakarta.DriverJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverPersistenceAdapter implements DriverAddPort, DriverUpdatePort, DriverDeletePort {

  private final DriverRepository driverRepository;
  private final DriverAdapterMapper driverAdapterMapper;
  private final CallSignHandler callSignHandler;
  private final FirmHandler firmHandler;
  private final FriendshipHandler friendshipHandler;

  @Override
  public Driver add(final Driver domain) {
    final var driverToSave = driverAdapterMapper.mapToEntity(domain);
    final var driverSaved = driverRepository.save(driverToSave);
    callSignHandler.addHandle(domain, driverSaved);
    firmHandler.addHandle(domain, driverSaved);
    friendshipHandler.addHandle(domain, driverSaved);

    return driverAdapterMapper.mapToDomain(driverSaved);
  }

  @Override
  public Driver update(final Driver domain) {
    final var driverId = domain.getId();
    final var entityFromDatabase = driverRepository.findById(driverId);
    final var updatedEntity = updateSavedEntity(entityFromDatabase, domain);
    callSignHandler.updateHandle(domain, updatedEntity);
    firmHandler.updateHandle(domain, updatedEntity);
    friendshipHandler.updateHandle(domain, updatedEntity);

    return driverAdapterMapper.mapToDomain(driverRepository.save(updatedEntity));
  }

  private DriverJakartaEntity updateSavedEntity(
      final DriverJakartaEntity entity, final Driver domain) {
    entity.setActive(domain.getActive());
    entity.setFirstName(domain.getFirstName());
    entity.setLastName(domain.getLastName());
    entity.setTaxNumber(domain.getTaxNumber());
    entity.setPhone(domain.getPhone());
    entity.setEmail(domain.getEmail());
    entity.setAddress(domain.getAddress());
    entity.setLegalEntityType(domain.getLegalEntityType().name());
    entity.setLhvAccount(domain.getLhvAccount());
    entity.setCompanyName(domain.getCompanyName());
    entity.setCompanyCeoFirstName(domain.getCompanyCeoFirstName());
    entity.setCompanyCeoLastName(domain.getCompanyCeoLastName());
    entity.setCompanyCeoTaxNumber(domain.getCompanyCeoTaxNumber());
    entity.setCompanyRegistrationNumber(domain.getCompanyRegistrationNumber());
    entity.setCompanyAddress(domain.getCompanyAddress());
    entity.setCompanyVat(domain.getCompanyVat());
    entity.setDriverLicenseNumber(domain.getDriverLicenseNumber());
    entity.setDriverLicenseExp(domain.getDriverLicenseExp());
    entity.setTaxiLicense(domain.getTaxiLicense());
    entity.setNeedInvoicesByEmail(domain.getNeedInvoicesByEmail());
    entity.setNeedFee(domain.getNeedFee());
    entity.setByTelegram(domain.getByTelegram());
    entity.setByWhatsApp(domain.getByWhatsApp());
    entity.setByViber(domain.getByViber());
    entity.setByEmail(domain.getByEmail());
    entity.setBySms(domain.getBySms());
    entity.setByPhone(domain.getByPhone());
    entity.setQFirmId(domain.getQFirmId());
    entity.setDeposit(domain.getDeposit());
    entity.setRequiredObligation(domain.getRequiredObligation());
    entity.setComment(domain.getComment());

    return entity;
  }

  @Override
  public void delete(Long id) {
    driverRepository.deleteById(id);
  }
}
