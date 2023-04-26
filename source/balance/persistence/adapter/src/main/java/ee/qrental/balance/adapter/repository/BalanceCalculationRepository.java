package ee.qrental.balance.adapter.repository;

import ee.qrental.invoice.entity.jakarta.BalanceCalculationJakartaEntity;
import java.time.LocalDate;
import java.util.List;

public interface BalanceCalculationRepository {
  BalanceCalculationJakartaEntity save(final BalanceCalculationJakartaEntity entity);

  List<BalanceCalculationJakartaEntity> findAll();

  BalanceCalculationJakartaEntity getReferenceById(final Long id);

  LocalDate getLastCalculationDate();
}
