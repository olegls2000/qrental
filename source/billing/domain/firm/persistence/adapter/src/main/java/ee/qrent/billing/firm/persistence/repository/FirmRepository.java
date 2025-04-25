package ee.qrent.billing.firm.persistence.repository;

import ee.qrent.billing.firm.persistence.entity.jakarta.FirmJakartaEntity;
import java.util.List;

public interface FirmRepository {
  List<FirmJakartaEntity> findAll();

  FirmJakartaEntity save(final FirmJakartaEntity entity);

  FirmJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
