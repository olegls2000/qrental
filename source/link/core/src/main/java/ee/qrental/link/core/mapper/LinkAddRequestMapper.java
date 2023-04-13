package ee.qrental.link.core.mapper;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.link.api.in.request.LinkAddRequest;
import ee.qrental.link.domain.Link;

public class LinkAddRequestMapper
        implements AddRequestMapper<LinkAddRequest, Link> {

    @Override
    public Link toDomain(LinkAddRequest request) {
        return Link.builder()
                .id(null)
                .carId(request.getCarId())
                .driverId(request.getDriverId())
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .comment(request.getComment())
                .build();
    }
}
