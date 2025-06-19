package ee.qrent.billing.insurance.core.mapper;

import ee.qrent.billing.insurance.api.in.request.InsuranceCalculationAddRequest;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;
import ee.qrent.common.in.time.QDateTime;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class InsuranceCalculationAddRequestMapper {
  private final QDateTime qDateTime;

  public InsuranceCalculation toDomain(final InsuranceCalculationAddRequest request) {
    if (request == null) {

      return null;
    }

    return InsuranceCalculation.builder()
        .actionDate(qDateTime.getToday())
        .qWeekId(request.getQWeekId())
        .insuranceCaseBalances(new ArrayList<>())
        .comment(request.getComment())
        .build();
  }
}
