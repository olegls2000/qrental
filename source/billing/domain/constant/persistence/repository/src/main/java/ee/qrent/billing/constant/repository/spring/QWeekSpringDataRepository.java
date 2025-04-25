package ee.qrent.billing.constant.repository.spring;

import ee.qrent.billing.constant.persistence.entity.jakarta.QWeekJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QWeekSpringDataRepository extends JpaRepository<QWeekJakartaEntity, Long> {

  QWeekJakartaEntity findByYearAndNumber(final Integer year, final Integer number);

  List<QWeekJakartaEntity> findByYear(final Integer year);

  @Query(
      value =
          "select qw.* from q_week qw "
                  + " WHERE "
                  + "(qw.year * 100 + qw.number) >= (select (q_week.year * 100 + q_week.number) from q_week WHERE q_week.id = :startWeekId) "
                  + "AND "
                  + "(qw.year * 100 + qw.number) <= (select (q_week.year * 100 + q_week.number) from q_week WHERE q_week.id = :endWeekId)",
      nativeQuery = true)
  List<QWeekJakartaEntity> findAllBetweenByIds(
      @Param("startWeekId") final Long startWeekId, @Param("endWeekId") final Long endWeekId);

  @Query(
      value =
          "SELECT qw.* FROM q_week qw "
              + "WHERE ("
              + "qw.year = (SELECT year FROM q_week WHERE id = :id) "
              + "AND qw.number < (SELECT number FROM q_week WHERE id = :id)) "
              + "OR qw.year < (SELECT year FROM q_week WHERE id = :id);",
      nativeQuery = true)
  List<QWeekJakartaEntity> findAllBeforeById(@Param("id") final Long id);

  @Query(
      value =
          "SELECT qw.* FROM q_week qw "
              + "WHERE ("
              + "qw.year = (SELECT year FROM q_week WHERE id = :id) "
              + "AND qw.number > (SELECT number FROM q_week WHERE id = :id)) "
              + "OR qw.year > (SELECT year FROM q_week WHERE id = :id);",
      nativeQuery = true)
  List<QWeekJakartaEntity> findAllAfterById(@Param("id") final Long id);
}
