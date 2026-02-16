package ee.qrent.billing.car.core.service;

import ee.qrent.billing.car.api.in.query.GetBrandingVerificationCalculationQuery;
import ee.qrent.billing.car.api.in.response.BrandingVerificationCalculationResultResponse;
import ee.qrent.billing.car.api.in.response.BrandingVerificationCalculationSummaryResponse;
import ee.qrent.billing.car.api.out.BrandingVerificationCalculationResultLoadPort;
import ee.qrent.billing.car.core.mapper.BrandingVerificationCalculationResultResponseMapper;
import ee.qrent.billing.car.core.mapper.BrandingVerificationCalculationSummaryResponseMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrandingVerificationCalculationQueryService
    implements GetBrandingVerificationCalculationQuery {

  private final BrandingVerificationCalculationResultLoadPort loadPort;
  private final BrandingVerificationCalculationResultResponseMapper mapper;
  private final BrandingVerificationCalculationSummaryResponseMapper summaryMapper;

  @Override
  public List<BrandingVerificationCalculationSummaryResponse> getAllCalculations() {
    return loadPort.loadAllCalculations().stream()
        .map(summaryMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public List<BrandingVerificationCalculationResultResponse> getResultsByCalculationId(
      final Long id) {
    return loadPort.loadResultsByCalculationId(id).stream()
        .map(mapper::toResponse)
        .collect(Collectors.toList());
  }
}
