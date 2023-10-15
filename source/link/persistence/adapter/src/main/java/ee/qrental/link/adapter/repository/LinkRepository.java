package ee.qrental.link.adapter.repository;

import ee.qrental.link.entity.jakarta.LinkJakartaEntity;
import java.time.LocalDate;
import java.util.List;

public interface LinkRepository {
  List<LinkJakartaEntity> findAll();

  LinkJakartaEntity save(final LinkJakartaEntity entity);

  LinkJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  LinkJakartaEntity findActiveByDriverIdAndNowDate(final Long driverId, final LocalDate nowDate);

  List<LinkJakartaEntity> findActiveByCarIdAndNowDate(final Long carId, final LocalDate nowDate);
}
