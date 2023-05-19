package ee.qrental.driver.adapter.repository;

import ee.qrental.driver.entity.jakarta.CallSignLinkJakartaEntity;
import java.time.LocalDate;
import java.util.List;

public interface CallSignLinkRepository {
  CallSignLinkJakartaEntity save(final CallSignLinkJakartaEntity entity);

  void deleteById(final Long id);

  CallSignLinkJakartaEntity getReferenceById(final Long id);

  List<CallSignLinkJakartaEntity> findAllByCallSignId(final Long callSignId);

  List<CallSignLinkJakartaEntity> findAll();

  CallSignLinkJakartaEntity findOneByDateEndIsNullAndCallSignId(final Long callSignId);

  CallSignLinkJakartaEntity findOneByDateEndIsNullAndDriverId(final Long driverId);

  CallSignLinkJakartaEntity findOneByDriverIdAndDate(final Long driverId, final LocalDate date);
}
