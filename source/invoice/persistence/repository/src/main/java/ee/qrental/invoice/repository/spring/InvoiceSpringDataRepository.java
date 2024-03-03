package ee.qrental.invoice.repository.spring;

import ee.qrental.invoice.entity.jakarta.InvoiceJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvoiceSpringDataRepository extends JpaRepository<InvoiceJakartaEntity, Long> {
  @Query(
      value =
          "SELECT inv.* FROM invoice inv "
              + "where inv.q_week_id= :qWeekId "
              + "and inv.driver_id = :driverId "
              + "and inv.q_firm_id = :qFirmId",
      nativeQuery = true)
  InvoiceJakartaEntity findByQWeekIdAndDriverIdAndQFirmId(
      final Long qWeekId, final Long driverId, final Long qFirmId);

  @Query(
      value =
          "SELECT inv.* FROM invoice inv "
              + "where inv.q_week_id = :qWeekId "
              + "and inv.driver_id = :driverId",
      nativeQuery = true)
  InvoiceJakartaEntity findByByQWeekIdAndDriverId(
      @Param("qWeekId") final Long qWeekId, @Param("driverId") final Long driverId);

  @Query(
          value =
                  "select inv.* from invoice inv "
                          + " LEFT JOIN invoice_calculation_result invr ON inv.id = invr.invoice_id "
                          + " where invr.calculation_id =:calculationId",
          nativeQuery = true)
  List<InvoiceJakartaEntity> findByCalculationId(@Param("calculationId") final Long calculationId);
}
