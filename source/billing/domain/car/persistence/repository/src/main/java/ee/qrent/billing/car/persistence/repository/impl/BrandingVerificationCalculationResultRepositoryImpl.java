package ee.qrent.billing.car.persistence.repository.impl;

import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationCalculationResultJakartaEntity;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationResultProjection;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationResultRepository;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationSummaryProjection;
import ee.qrent.billing.car.persistence.repository.spring.BrandingVerificationCalculationResultSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrandingVerificationCalculationResultRepositoryImpl
    implements BrandingVerificationCalculationResultRepository {

  private final BrandingVerificationCalculationResultSpringDataRepository springDataRepository;

  @Override
  public BrandingVerificationCalculationResultJakartaEntity save(
      final BrandingVerificationCalculationResultJakartaEntity entity) {
    return springDataRepository.save(entity);
  }

  @Override
  public List<BrandingVerificationCalculationResultProjection> findAllResults() {
    return springDataRepository.findAllResults();
  }

  @Override
  public List<BrandingVerificationCalculationResultProjection> findResultsByCalculationId(
      final Long id) {
    return springDataRepository.findResultsByCalculationId(id);
  }

  @Override
  public List<BrandingVerificationCalculationSummaryProjection> findAllCalculations() {
    return springDataRepository.findAllCalculations();
  }
}
