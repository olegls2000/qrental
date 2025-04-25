package ee.qrent.billing.contract.core.mapper;

import ee.qrent.common.in.mapper.UpdateRequestMapper;
import ee.qrent.billing.contract.api.in.request.AbsenceUpdateRequest;
import ee.qrent.billing.contract.api.out.AbsenceLoadPort;
import ee.qrent.billing.contract.domain.Absence;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AbsenceUpdateRequestMapper
    implements UpdateRequestMapper<AbsenceUpdateRequest, Absence> {

  private final AbsenceLoadPort loadPort;

  @Override
  public Absence toDomain(final AbsenceUpdateRequest request) {
    final var absenceFromDb = loadPort.loadById(request.getId());
    absenceFromDb.setDriverId(request.getDriverId());
    absenceFromDb.setDateStart(request.getDateStart());
    absenceFromDb.setDateEnd(request.getDateEnd());
    absenceFromDb.setWithCar(request.getWithCar());
    absenceFromDb.setReason(request.getReason());
    absenceFromDb.setComment(request.getComment());

    return absenceFromDb;
  }

  @Override
  public AbsenceUpdateRequest toRequest(final Absence domain) {
    return AbsenceUpdateRequest.builder()
        .id(domain.getId())
        .driverId(domain.getDriverId())
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .withCar(domain.getWithCar())
        .reason(domain.getReason())
        .comment(domain.getComment())
        .build();
  }
}
