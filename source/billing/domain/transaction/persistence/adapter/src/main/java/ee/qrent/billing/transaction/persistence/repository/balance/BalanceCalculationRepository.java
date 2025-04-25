package ee.qrent.billing.transaction.persistence.repository.balance;


import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceCalculationJakartaEntity;
import java.time.LocalDate;
import java.util.List;

public interface BalanceCalculationRepository {
  BalanceCalculationJakartaEntity save(final BalanceCalculationJakartaEntity entity);

  List<BalanceCalculationJakartaEntity> findAll();

  BalanceCalculationJakartaEntity getReferenceById(final Long id);

  LocalDate getLastCalculationDate();
}
