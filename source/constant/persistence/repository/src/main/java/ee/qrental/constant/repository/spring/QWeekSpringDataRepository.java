package ee.qrental.constant.repository.spring;

import ee.qrental.constant.entity.jakarta.QWeekJakartaEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QWeekSpringDataRepository extends JpaRepository<QWeekJakartaEntity, Long> {

  QWeekJakartaEntity findByYearAndNumber(final Integer year, final Integer number);

  List<QWeekJakartaEntity> findByYear(final Integer year);

  @Query(value = "SELECT distinct (qw.year) FROM q_week qw;", nativeQuery = true)
  List<Integer> findYears();

  @Query(
      value =
          "SELECT qw.* FROM q_week qw "
              + "WHERE qw.year <= (SELECT year FROM q_week WHERE id = :id) "
              + "AND qw.number <= (SELECT number FROM q_week WHERE id = :id);",
      nativeQuery = true)
  List<QWeekJakartaEntity> findAllBeforeById(@Param("id") final Long id);
}
