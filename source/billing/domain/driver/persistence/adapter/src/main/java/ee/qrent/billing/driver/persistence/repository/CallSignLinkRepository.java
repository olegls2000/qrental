package ee.qrent.billing.driver.persistence.repository;

import ee.qrent.billing.driver.persistence.entity.jakarta.CallSignLinkJakartaEntity;
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

  CallSignLinkJakartaEntity findActiveByDriverIdAndNowDate(final Long driverId, final LocalDate nowDate);

  List<CallSignLinkJakartaEntity> findActiveByDate(final LocalDate date);

  Long findCountActiveByDate(final LocalDate date);

  List<CallSignLinkJakartaEntity> findClosedByDate(final LocalDate date);

  Long findCountClosedByDate(final LocalDate date);

  List<CallSignLinkJakartaEntity> findAllByDriverId(Long driverId);
}
