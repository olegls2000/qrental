package ee.qrental.transaction.repository.spring;

import ee.qrental.transaction.entity.jakarta.TransactionJakartaEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionSpringDataRepository
    extends JpaRepository<TransactionJakartaEntity, Long> {

  List<TransactionJakartaEntity> findByDriverId(Long driverId);

  List<TransactionJakartaEntity> findAllByDateBetween(LocalDate dateStart, LocalDate dateEnd);

  List<TransactionJakartaEntity> findAllByDateBetweenAndDriverId(
      LocalDate dateStart, LocalDate dateEnd, Long driverId);

  @Query(
      value =
          "SELECT * FROM transaction tx "
              + "WHERE tx.id  in ("
              + "select clr.transaction_id from rent_calculation_result clr "
              + "where clr.calculation_id = :calculationId)",
      nativeQuery = true)
  List<TransactionJakartaEntity> findAllByCalculationId(@Param("calculationId") Long calculationId);
}
