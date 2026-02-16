package ee.qrent.billing.car.api.out;

import ee.qrent.billing.car.domain.BrandingVerificationCalculationResultRow;
import ee.qrent.billing.car.domain.BrandingVerificationCalculationSummaryRow;
import java.util.List;

public interface BrandingVerificationCalculationResultLoadPort {
  List<BrandingVerificationCalculationSummaryRow> loadAllCalculations();

  List<BrandingVerificationCalculationResultRow> loadAllResults();

  List<BrandingVerificationCalculationResultRow> loadResultsByCalculationId(final Long id);
}
