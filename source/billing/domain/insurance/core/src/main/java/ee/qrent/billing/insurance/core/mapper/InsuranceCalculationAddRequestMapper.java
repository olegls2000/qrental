package ee.qrent.billing.insurance.core.mapper;

import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;

import java.util.ArrayList;

public class InsuranceCalculationAddRequestMapper {

  public InsuranceCalculation toDomain(final InsuranceCalculationAddRequest request) {
    if (request == null) {

      return null;
    }

    return InsuranceCalculation.builder()
        .actionDate(request.getActionDate())
        .qWeekId(request.getQWeekId())
        .insuranceCaseBalances(new ArrayList<>())
        .comment(request.getComment())
        .build();
  }
}
