package ee.qrental.transaction.repository.spring.rent;

import ee.qrental.transaction.entity.jakarta.rent.RentCalculationJakartaEntity;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RentCalculationSpringDataRepository
    extends JpaRepository<RentCalculationJakartaEntity, Long> {

  @Query(
      value = "select end_date from rent_calculation order by end_date desc limit 1",
      nativeQuery = true)
  LocalDate getLastCalculationDate();
}
