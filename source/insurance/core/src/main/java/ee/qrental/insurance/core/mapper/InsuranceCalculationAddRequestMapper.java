package ee.qrental.insurance.core.mapper;

import ee.qrental.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrental.insurance.domain.InsuranceCalculation;

public class InsuranceCalculationAddRequestMapper {

  public InsuranceCalculation toDomain(final InsuranceCalculationAddRequest request) {
    return InsuranceCalculation.builder()
        .actionDate(request.getActionDate())
        .startQWeekId(null)
        .endQWeekId(request.getQWeekId())
        .comment(request.getComment())
        .build();
  }
}
