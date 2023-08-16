package ee.qrental.driver.adapter.repository;

import ee.qrental.driver.entity.jakarta.FirmLinkJakartaEntity;

import java.time.LocalDate;
import java.util.List;

public interface FirmLinkRepository {
  FirmLinkJakartaEntity save(final FirmLinkJakartaEntity entity);

  void deleteById(final Long id);

  FirmLinkJakartaEntity getReferenceById(final Long id);
  FirmLinkJakartaEntity findOneByDriverIdAndRequiredDate(final Long driverId, final LocalDate requiredDatee);
  List<FirmLinkJakartaEntity> findAll();


}
