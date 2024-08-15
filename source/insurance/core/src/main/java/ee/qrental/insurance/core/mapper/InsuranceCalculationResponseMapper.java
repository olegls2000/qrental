package ee.qrental.insurance.core.mapper;

import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.in.response.InsuranceCalculationResponse;
import ee.qrental.insurance.domain.InsuranceCalculation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationResponseMapper
    implements ResponseMapper<InsuranceCalculationResponse, InsuranceCalculation> {

  private final GetQWeekQuery qWeekQuery;

  @Override
  public InsuranceCalculationResponse toResponse(final InsuranceCalculation domain) {

    final var startWeekId = domain.getStartQWeekId();
    final var startWeek = qWeekQuery.getById(startWeekId);
    final var endWeekId = domain.getEndQWeekId();
    final var endWeek = qWeekQuery.getById(endWeekId);

    return InsuranceCalculationResponse.builder()
        .id(domain.getId())
        .startWeek(getWeekString(domain.getStartQWeekId()))
        .endWeek(getWeekString(domain.getEndQWeekId()))
        .actionDate(domain.getActionDate())
        .build();
  }

  @Override
  public String toObjectInfo(final InsuranceCalculation domain) {

    return format(
        "Insurance Calculation was made on: %s. For the period: %s ... %s ",
        domain.getActionDate().toString(),
        getWeekString(domain.getStartQWeekId()),
        getWeekString(domain.getEndQWeekId()));
  }

  private String getWeekString(final Long qWeekId) {
    final var qWeek = qWeekQuery.getById(qWeekId);

    return format("%d - %d", qWeek.getYear(), qWeek.getNumber());
  }
}
