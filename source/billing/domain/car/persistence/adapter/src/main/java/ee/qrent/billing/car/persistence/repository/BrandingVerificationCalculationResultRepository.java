package ee.qrent.billing.car.persistence.repository;

import ee.qrent.billing.car.persistence.entity.jakarta.BrandingVerificationCalculationResultJakartaEntity;
import java.util.List;

public interface BrandingVerificationCalculationResultRepository {
  BrandingVerificationCalculationResultJakartaEntity save(
      final BrandingVerificationCalculationResultJakartaEntity entity);

  List<BrandingVerificationCalculationResultProjection> findAllResults();

  List<BrandingVerificationCalculationResultProjection> findResultsByCalculationId(final Long id);

  List<BrandingVerificationCalculationSummaryProjection> findAllCalculations();
}
