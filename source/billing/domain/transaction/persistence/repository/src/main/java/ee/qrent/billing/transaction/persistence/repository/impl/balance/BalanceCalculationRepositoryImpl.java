package ee.qrent.billing.transaction.persistence.repository.impl.balance;

import ee.qrent.billing.transaction.persistence.repository.balance.BalanceCalculationRepository;
import ee.qrent.billing.transaction.persistence.entity.jakarta.balance.BalanceCalculationJakartaEntity;
import ee.qrent.billing.transaction.persistence.repository.spring.balance.BalanceCalculationSpringDataRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationRepositoryImpl implements BalanceCalculationRepository {

  private final BalanceCalculationSpringDataRepository springDataRepository;

  @Override
  public BalanceCalculationJakartaEntity save(final BalanceCalculationJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public List<BalanceCalculationJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public BalanceCalculationJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public LocalDate getLastCalculationDate() {
    return springDataRepository.getLastCalculationDate();
  }
}
