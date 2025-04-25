package ee.qrent.billing.car.core.mapper;

import ee.qrent.billing.car.api.in.request.CarLinkAddRequest;
import ee.qrent.billing.car.domain.CarLink;
import ee.qrent.common.in.mapper.AddRequestMapper;

public class CarLinkAddRequestMapper
        implements AddRequestMapper<CarLinkAddRequest, CarLink> {

    @Override
    public CarLink toDomain(CarLinkAddRequest request) {
        return CarLink.builder()
                .id(null)
                .carId(request.getCarId())
                .driverId(request.getDriverId())
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .comment(request.getComment())
                .build();
    }
}
