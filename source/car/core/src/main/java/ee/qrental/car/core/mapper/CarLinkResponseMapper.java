package ee.qrental.car.core.mapper;

import static java.lang.String.format;

import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.car.api.in.response.CarLinkResponse;
import ee.qrental.car.domain.CarLink;
import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.query.GetCallSignLinkQuery;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CarLinkResponseMapper implements ResponseMapper<CarLinkResponse, CarLink> {

  private final GetCallSignLinkQuery callSignLinkQuery;
  private final GetDriverQuery driverQuery;
  private final GetCarQuery carQuery;

  private Integer getCallSign(final Long driverId) {
    final var callSignLink = callSignLinkQuery.getActiveCallSignLinkByDriverId(driverId);
    if (callSignLink == null) {
      System.out.println(
          String.format("Driver with id %d doesnt have active Call Sign Links", driverId));
      return null;
    }

    return callSignLink.getCallSign();
  }

  @Override
  public CarLinkResponse toResponse(final CarLink domain) {
    if (domain == null) {
      return null;
    }

    final var carId = domain.getCarId();
    final var driverId = domain.getDriverId();
    final var callSign = getCallSign(driverId);
    final var carInfo = carQuery.getObjectInfo(carId);
    final var car = carQuery.getById(carId);
    final var driverInfo = driverQuery.getObjectInfo(driverId);
    return CarLinkResponse.builder()
        .id(domain.getId())
        .carId(domain.getCarId())
        .carInfo(carInfo)
        .registrationNumber(car.getRegNumber())
        .driverId(domain.getDriverId())
        .driverInfo(driverInfo)
        .callSign(callSign)
        .dateStart(domain.getDateStart())
        .dateEnd(domain.getDateEnd())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final CarLink domain) {
    final var driverId = domain.getDriverId();
    final var carId = domain.getCarId();
    var callSignInfo = "";
    final var callSignLink = callSignLinkQuery.getActiveCallSignLinkByDriverId(driverId);

    if (callSignLink != null) {
      final var callSign = callSignLink.getCallSign();
      callSignInfo = "Call sign: " + callSign + ", ";
    }

    final var driverInfo = driverQuery.getObjectInfo(driverId);
    final var carInfo = carQuery.getObjectInfo(carId);
    return callSignInfo
        + format(
            "Driver: %s and Car: %s active from %s", driverInfo, carInfo, domain.getDateStart());
  }
}
