package ee.qrental.link.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.link.api.in.request.LinkUpdateRequest;
import ee.qrental.link.domain.Link;

public class LinkUpdateRequestMapper implements UpdateRequestMapper<LinkUpdateRequest, Link> {

    @Override
    public Link toDomain(final LinkUpdateRequest request) {
        return Link.builder()
                .id(request.getId())
                .carId(request.getCarId())
                .driverId(request.getDriverId())
                .dateStart(request.getDateStart())
                .dateEnd(request.getDateEnd())
                .comment(request.getComment())
                .build();
    }

    @Override
    public LinkUpdateRequest toRequest(final Link domain) {
        return LinkUpdateRequest.builder()
                .id(domain.getId())
                .carId(domain.getCarId())
                .driverId(domain.getDriverId())
                .dateStart(domain.getDateStart())
                .dateEnd(domain.getDateEnd())
                .comment(domain.getComment())
                .build();
    }
}
