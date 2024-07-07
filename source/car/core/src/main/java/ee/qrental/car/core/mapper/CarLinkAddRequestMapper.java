package ee.qrental.car.core.mapper;

import ee.qrental.car.api.in.request.CarLinkAddRequest;
import ee.qrental.car.domain.CarLink;
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
