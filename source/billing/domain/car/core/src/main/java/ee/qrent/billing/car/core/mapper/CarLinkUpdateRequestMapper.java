package ee.qrent.billing.car.core.mapper;

import ee.qrent.billing.car.api.in.request.CarLinkUpdateRequest;
import ee.qrent.billing.car.domain.CarLink;
import ee.qrent.common.in.mapper.UpdateRequestMapper;

public class CarLinkUpdateRequestMapper implements UpdateRequestMapper<CarLinkUpdateRequest, CarLink> {

    @Override
    public CarLink toDomain(final CarLinkUpdateRequest request) {
        return CarLink.builder()
                .id(request.getId())
                .carId(request.getCarId())
                .driverId(request.getDriverId())
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .comment(request.getComment())
                .build();
    }

    @Override
    public CarLinkUpdateRequest toRequest(final CarLink domain) {
        return CarLinkUpdateRequest.builder()
                .id(domain.getId())
                .carId(domain.getCarId())
                .driverId(domain.getDriverId())
                .dateStart(domain.getDateStart())
                .dateEnd(domain.getDateEnd())
                .comment(domain.getComment())
                .build();
    }
}
