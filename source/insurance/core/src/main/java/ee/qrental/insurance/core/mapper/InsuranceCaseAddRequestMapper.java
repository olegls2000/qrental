package ee.qrental.insurance.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.insurance.api.in.request.InsuranceCaseAddRequest;
import ee.qrental.insurance.domain.InsuranceCase;
import lombok.AllArgsConstructor;

import static java.lang.Boolean.TRUE;

@AllArgsConstructor
public class InsuranceCaseAddRequestMapper
    implements AddRequestMapper<InsuranceCaseAddRequest, InsuranceCase> {

  @Override
  public InsuranceCase toDomain(InsuranceCaseAddRequest request) {
    return InsuranceCase.builder()
        .id(null)
        .driverId(request.getDriverId())
        .carId(request.getCarId())
        .damageAmount(request.getDamageAmount())
        .occurrenceDate(request.getOccurrenceDate())
        .description(request.getDescription())
        .active(TRUE)
        .build();
  }
}
