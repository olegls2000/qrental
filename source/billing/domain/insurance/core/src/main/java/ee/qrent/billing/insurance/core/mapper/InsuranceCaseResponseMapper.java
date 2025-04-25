package ee.qrent.billing.insurance.core.mapper;

import static ee.qrent.common.utils.QNumberUtils.qRound;
import static java.lang.String.format;

import ee.qrent.common.in.mapper.ResponseMapper;

import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.insurance.api.in.response.InsuranceCaseResponse;
import ee.qrent.billing.insurance.domain.InsuranceCase;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseResponseMapper
    implements ResponseMapper<InsuranceCaseResponse, InsuranceCase> {

  private final GetDriverQuery driverQuery;
  private final GetCarQuery carQuery;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public InsuranceCaseResponse toResponse(final InsuranceCase domain) {

    final var driver = driverQuery.getById(domain.getDriverId());
    final var car = carQuery.getById(domain.getCarId());
    final var qWeek = qWeekQuery.getById(domain.getStartQWeekId());
    return InsuranceCaseResponse.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .driverId(domain.getDriverId())
        .driverInfo(
            format("%s %s, %d", driver.getFirstName(), driver.getLastName(), driver.getIsikukood()))
        .carId(domain.getCarId())
        .carInfo(format("%s %s, %s", car.getManufacturer(), car.getModel(), car.getRegNumber()))
        .occurrenceDate(domain.getOccurrenceDate())
        .occurrenceWeekInfo(format("%d - %d", qWeek.getYear(), qWeek.getNumber()))
        .damageAmount(qRound(domain.getDamageAmount()))
        .description(domain.getDescription())
        .build();
  }

  @Override
  public String toObjectInfo(final InsuranceCase domain) {

    return format(
        "InsuranceCase damage amount: %s, Driver: %d, Car: %d, Occurrence date: %s",
        domain.getDamageAmount(),
        domain.getDriverId(),
        domain.getCarId(),
        domain.getOccurrenceDate().toString());
  }
}
