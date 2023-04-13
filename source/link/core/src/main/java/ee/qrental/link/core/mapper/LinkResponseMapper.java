package ee.qrental.link.core.mapper;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.link.api.in.response.LinkResponse;
import ee.qrental.link.domain.Link;

import static java.lang.String.format;

public class LinkResponseMapper
        implements ResponseMapper<LinkResponse, Link> {
    @Override
    public LinkResponse toResponse(final Link domain) {
    return LinkResponse.builder()
            .id(domain.getId())
            .carId(domain.getCarId())
            .driverId(domain.getDriverId())
            .dateStart(domain.getDateStart())
            .dateEnd(domain.getDateEnd())
            .comment(domain.getComment())
            .build();
    }

    @Override
    public String toObjectInfo(Link domain) {
        return format("%s %s",
                domain.getCarId(),
                domain.getDriverId());
    }
}
