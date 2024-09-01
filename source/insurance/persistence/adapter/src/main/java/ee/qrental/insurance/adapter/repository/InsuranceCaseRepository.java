package ee.qrental.insurance.adapter.repository;

import ee.qrental.insurance.entity.jakarta.InsuranceCaseJakartaEntity;
import java.util.List;

public interface InsuranceCaseRepository {
  List<InsuranceCaseJakartaEntity> findAll();

  List<InsuranceCaseJakartaEntity> findActive();

  List<InsuranceCaseJakartaEntity> findActiveByDriverId(final Long driverId);

  InsuranceCaseJakartaEntity save(final InsuranceCaseJakartaEntity entity);

  InsuranceCaseJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
