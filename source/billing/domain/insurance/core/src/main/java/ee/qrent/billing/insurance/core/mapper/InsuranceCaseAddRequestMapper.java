package ee.qrent.billing.insurance.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseAddRequest;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import lombok.AllArgsConstructor;

import static java.lang.Boolean.TRUE;

@AllArgsConstructor
public class InsuranceCaseAddRequestMapper
    implements AddRequestMapper<InsuranceCaseAddRequest, InsuranceCase> {
  private final GetQWeekQuery qWeekQuery;

  @Override
  public InsuranceCase toDomain(InsuranceCaseAddRequest request) {
    final var qWeekId = qWeekQuery.getByDate(request.getOccurrenceDate()).getId();
    return InsuranceCase.builder()
        .id(null)
        .driverId(request.getDriverId())
        .carId(request.getCarId())
        .damageAmount(request.getDamageAmount())
        .occurrenceDate(request.getOccurrenceDate())
        .startQWeekId(qWeekId)
        .description(request.getDescription())
        .active(TRUE)
        .build();
  }
}
