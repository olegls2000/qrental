package ee.qrental.invoice.repository.spring;

import ee.qrental.invoice.entity.jakarta.InvoiceJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceSpringDataRepository extends JpaRepository<InvoiceJakartaEntity, Long> {
  @Query(
      value =
          "SELECT inv.* FROM invoice inv "
              + "where inv.week_number= :wekNumber "
              + "and inv.driver_id = :driverId "
              + "and inv.q_firm_id = :qFirmId",
      nativeQuery = true)
  InvoiceJakartaEntity findByWeekNumberAndDriverIdAndQFirmId(
      final Integer wekNumber, final Long driverId, final Long qFirmId);

  @Query(
      value =
          "SELECT inv.* FROM invoice inv "
              + "where inv.q_week_id = :qWeekId "
              + "and inv.driver_id = :driverId",
      nativeQuery = true)
  InvoiceJakartaEntity findByByQWeekIdAndDriverId(
      @Param("qWeekId") final Long qWeekId, @Param("driverId") final Long driverId);
}
