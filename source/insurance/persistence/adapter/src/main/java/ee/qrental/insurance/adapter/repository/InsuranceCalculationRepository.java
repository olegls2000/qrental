package ee.qrental.insurance.adapter.repository;


import ee.qrental.insurance.entity.jakarta.InsuranceCalculationJakartaEntity;
import java.util.List;

public interface InsuranceCalculationRepository {
  InsuranceCalculationJakartaEntity save(final InsuranceCalculationJakartaEntity entity);

  List<InsuranceCalculationJakartaEntity> findAll();

  InsuranceCalculationJakartaEntity getReferenceById(final Long id);

  Long getLastCalculatedQWeekId();
}
