package ee.qrental.link.core.mapper;

import ee.qrental.callsign.api.in.query.GetCallSignLinkQuery;
import ee.qrental.car.api.in.query.GetCarQuery;
import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.link.api.in.response.LinkResponse;
import ee.qrental.link.domain.Link;
import lombok.AllArgsConstructor;

import static java.lang.String.format;
@AllArgsConstructor
public class LinkResponseMapper  implements ResponseMapper<LinkResponse, Link> {

    private final GetCallSignLinkQuery callSignLinkQuery;
    private final GetDriverQuery driverQuery;
    private  final GetCarQuery carQuery;

    private Integer getCallSign(final Long driverId) {
        // TODO move logic to Call Sign domain
        final var callSignLink = callSignLinkQuery.getActiveCallSignLinkByDriverId(driverId);
        if (callSignLink == null) {
            System.out.println(
                    String.format("Driver with id %d doesnt have active Call Sign Links", driverId));
            return null;
        }

        return callSignLink.getCallSign();
    }





    @Override
    public LinkResponse toResponse(final Link domain) {
        final var carId = domain.getCarId();
        final var driverId = domain.getDriverId();
        final var callSign = getCallSign(driverId);
        final var carInfo = carQuery.getObjectInfo(carId);
        final var driverInfo = driverQuery.getObjectInfo(driverId);
    return LinkResponse.builder()
            .id(domain.getId())
            .carId(domain.getCarId())
            .carInfo(carInfo)
            .driverId(domain.getDriverId())
            .driverInfo(driverInfo)
            .callSign(callSign)
            .dateStart(domain.getDateStart())
            .dateEnd(domain.getDateEnd())
            .comment(domain.getComment())
            .build();
    }

    @Override
    public String toObjectInfo(final Link domain) {
        final var driverId = domain.getDriverId();
        final var carId = domain.getCarId();
        final var callSignLink = callSignLinkQuery.getActiveCallSignLinkByDriverId(driverId);
        final var callSign = callSignLink.getCallSign();
        final var driverInfo = driverQuery.getObjectInfo(driverId);
        final var carInfo = carQuery.getObjectInfo(carId);
        return format("%s %s %s ",
                callSign , carInfo, driverInfo );
    }
}
