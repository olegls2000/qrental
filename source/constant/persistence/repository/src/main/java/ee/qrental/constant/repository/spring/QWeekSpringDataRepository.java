package ee.qrental.constant.repository.spring;

import ee.qrental.constant.entity.jakarta.QWeekJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QWeekSpringDataRepository extends JpaRepository<QWeekJakartaEntity, Long> {

  QWeekJakartaEntity findByYearAndNumber(final Integer year, final Integer number);

  List<QWeekJakartaEntity> findByYear(final Integer year);

  @Query(value = "SELECT distinct (qw.year) FROM q_week qw;", nativeQuery = true)
  List<Integer> findYears();
}
