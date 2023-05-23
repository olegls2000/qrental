package ee.qrental.balance.repository.spring;

import ee.qrental.invoice.entity.jakarta.BalanceCalculationJakartaEntity;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BalanceCalculationSpringDataRepository
    extends JpaRepository<BalanceCalculationJakartaEntity, Long> {

  @Query(
      value = "select end_date from balance_calculation order by end_date desc limit 1",
      nativeQuery = true)
  LocalDate getLastCalculationDate();

  BalanceCalculationJakartaEntity findByActionDate(final LocalDate actionDate);
}
