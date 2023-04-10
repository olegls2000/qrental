package ee.qrental.callsign.adapter.repository;

import ee.qrental.callsign.entity.jakarta.CallSignLinkJakartaEntity;
import java.util.List;

public interface CallSignLinkRepository {
  CallSignLinkJakartaEntity save(final CallSignLinkJakartaEntity entity);

  void deleteById(final Long id);

  CallSignLinkJakartaEntity getReferenceById(final Long id);

  List<CallSignLinkJakartaEntity> findAll();

  List<CallSignLinkJakartaEntity> findAllByDateEndIsNull();

  CallSignLinkJakartaEntity findByDriverId(final Long driverId);
  Integer findCallSignByDriverId(final Long driverId);

}
