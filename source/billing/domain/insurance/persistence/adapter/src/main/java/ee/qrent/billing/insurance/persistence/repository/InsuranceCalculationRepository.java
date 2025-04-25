package ee.qrent.billing.insurance.persistence.repository;


import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCalculationJakartaEntity;
import java.util.List;

public interface InsuranceCalculationRepository {
  InsuranceCalculationJakartaEntity save(final InsuranceCalculationJakartaEntity entity);

  List<InsuranceCalculationJakartaEntity> findAll();

  InsuranceCalculationJakartaEntity getReferenceById(final Long id);

  Long getLastCalculatedQWeekId();
}
