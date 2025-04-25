package ee.qrent.billing.insurance.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.response.InsuranceCalculationResponse;
import ee.qrent.billing.insurance.domain.InsuranceCalculation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationResponseMapper
    implements ResponseMapper<InsuranceCalculationResponse, InsuranceCalculation> {

  private final GetQWeekQuery qWeekQuery;

  @Override
  public InsuranceCalculationResponse toResponse(final InsuranceCalculation domain) {

    return InsuranceCalculationResponse.builder()
        .id(domain.getId())
        .qWeek(getWeekString(domain.getQWeekId()))
        .actionDate(domain.getActionDate())
        .build();
  }

  @Override
  public String toObjectInfo(final InsuranceCalculation domain) {

    return format(
        "Insurance Calculation was made on: %s. For the period: %s ... %s ",
        domain.getActionDate().toString(), getWeekString(domain.getQWeekId()));
  }

  private String getWeekString(final Long qWeekId) {
    final var qWeek = qWeekQuery.getById(qWeekId);

    return format("%d - %d", qWeek.getYear(), qWeek.getNumber());
  }
}
