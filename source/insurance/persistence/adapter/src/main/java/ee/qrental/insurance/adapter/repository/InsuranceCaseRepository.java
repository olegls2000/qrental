package ee.qrental.insurance.adapter.repository;

import ee.qrental.insurance.entity.jakarta.InsuranceCaseJakartaEntity;
import java.util.List;

public interface InsuranceCaseRepository {
  List<InsuranceCaseJakartaEntity> findAll();

  List<InsuranceCaseJakartaEntity> findActiveByQWeekId(final Long qWeekId);

  List<InsuranceCaseJakartaEntity> findActiveByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId);

  InsuranceCaseJakartaEntity save(final InsuranceCaseJakartaEntity entity);

  InsuranceCaseJakartaEntity getReferenceById(final Long id);

  void deleteById(final Long id);
}
