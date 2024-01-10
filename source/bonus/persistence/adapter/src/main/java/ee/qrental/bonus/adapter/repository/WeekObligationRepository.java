package ee.qrental.bonus.adapter.repository;

import ee.qrental.bonus.entity.jakarta.WeekObligationJakartaEntity;
import java.util.List;

public interface WeekObligationRepository {
  List<WeekObligationJakartaEntity> findAll();

  WeekObligationJakartaEntity save(final WeekObligationJakartaEntity entity);

  WeekObligationJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
