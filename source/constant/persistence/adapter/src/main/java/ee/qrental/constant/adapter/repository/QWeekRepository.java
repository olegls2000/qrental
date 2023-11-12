package ee.qrental.constant.adapter.repository;

import ee.qrental.constant.entity.jakarta.QWeekJakartaEntity;
import java.util.List;

public interface QWeekRepository {
  List<QWeekJakartaEntity> findAll();

  QWeekJakartaEntity save(final QWeekJakartaEntity entity);

  QWeekJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  QWeekJakartaEntity findByYearAndWeekNumber(final Integer year, final Integer weekNumber);

  List<QWeekJakartaEntity> findByYear(final Integer year);

  List<QWeekJakartaEntity> findAllBetweenByIds(final Long startWeekId, final Long endWeekId);

  List<QWeekJakartaEntity> findAllBeforeById(final Long id);

  List<Integer> findAllYears();

  List<QWeekJakartaEntity> findAllAfterById(final Long id);
}
