package ee.qrent.billing.insurance.persistence.repository;

import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCaseJakartaEntity;
import java.util.List;

public interface InsuranceCaseRepository {
  List<InsuranceCaseJakartaEntity> findAll();

  List<InsuranceCaseJakartaEntity> findActiveByQWeekId(final Long qWeekId);

  List<InsuranceCaseJakartaEntity> findActiveByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId);

  InsuranceCaseJakartaEntity save(final InsuranceCaseJakartaEntity entity);

  InsuranceCaseJakartaEntity getReferenceById(final Long id);

  List<InsuranceCaseJakartaEntity> findActive();

  List<InsuranceCaseJakartaEntity> findClosed();

  Long findCountActive();

  Long findCountClosed();

  List<InsuranceCaseJakartaEntity> findActiveByDriverId(final Long driverId);

  List<Long> findPaymentTransactionIdsByInsuranceCaseId(final Long insuranceCaseId);
}
