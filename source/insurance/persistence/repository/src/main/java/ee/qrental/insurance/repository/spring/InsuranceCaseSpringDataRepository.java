package ee.qrental.insurance.repository.spring;

import ee.qrental.insurance.entity.jakarta.InsuranceCaseJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InsuranceCaseSpringDataRepository
    extends JpaRepository<InsuranceCaseJakartaEntity, Long> {

  List<InsuranceCaseJakartaEntity> findAllByActiveIsTrueAndDriverId(final Long driverId);

  @Query(
      value =
          "select ic.* from insurance_case ic "
              + "LEFT JOIN q_week qw on qw.id = ic.start_q_week_id "
              + "where "
              + "ic.active = true "
              + "and qw.year <= (select year from q_week where id = :qWeekId) "
              + "and qw.number <= (select number from q_week where id = :qWeekId);",
      nativeQuery = true)
  List<InsuranceCaseJakartaEntity> findAllByActiveIsTrueAndQweekId(final Long qWeekId);
}
