package ee.qrent.billing.deposit.persistence.repository;

import ee.qrent.billing.deposit.persistence.entity.jakarta.DepositJakartaEntity;
import java.util.List;

public interface DepositRepository {
  List<DepositJakartaEntity> findAll();

  DepositJakartaEntity save(final DepositJakartaEntity entity);

  DepositJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);

  List<DepositJakartaEntity> findAllByDriverId(final Long driverId);
}
