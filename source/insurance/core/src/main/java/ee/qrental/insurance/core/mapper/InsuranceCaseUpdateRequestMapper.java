package ee.qrental.insurance.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrental.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrental.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrental.insurance.domain.InsuranceCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseUpdateRequestMapper
    implements UpdateRequestMapper<InsuranceCaseUpdateRequest, InsuranceCase> {

  private final InsuranceCaseLoadPort loadPort;

  @Override
  public InsuranceCase toDomain(final InsuranceCaseUpdateRequest request) {
    final var fromDb = loadPort.loadById(request.getId());
    fromDb.setCarId(request.getCarId());
    fromDb.setDriverId(request.getDriverId());
    fromDb.setDamageAmount(request.getDamageAmount());
    fromDb.setOccurrenceDate(request.getOccurrenceDate());
    fromDb.setDescription(request.getDescription());

    return fromDb;
  }

  @Override
  public InsuranceCaseUpdateRequest toRequest(final InsuranceCase domain) {
    return InsuranceCaseUpdateRequest.builder()
        .id(domain.getId())
        .driverId(domain.getDriverId())
        .damageAmount(domain.getDamageAmount())
        .carId(domain.getCarId())
        .occurrenceDate(domain.getOccurrenceDate())
        .description(domain.getDescription())
        .build();
  }
}
