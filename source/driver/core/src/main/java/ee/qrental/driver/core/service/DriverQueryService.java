package ee.qrental.driver.core.service;


import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.core.mapper.DriverResponseMapper;
import ee.qrental.driver.core.mapper.DriverUpdateRequestMapper;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;


@AllArgsConstructor
public class DriverQueryService
        implements GetDriverQuery {

    private final DriverLoadPort loadPort;
    private final DriverResponseMapper mapper;
    private final DriverUpdateRequestMapper updateRequestMapper;

    @Override
    public List<DriverResponse> getAll() {
        return loadPort.loadAll()
                .stream()
                .map(mapper::toResponse)
                .collect(toList());
    }

    @Override
    public DriverResponse getById(final Long id) {
        return mapper.toResponse(loadPort.loadById(id));
    }

    @Override
    public String getObjectInfo(Long id) {
        return mapper.toObjectInfo(loadPort.loadById(id));
    }

    @Override
    public DriverUpdateRequest getUpdateRequestById(Long id) {
        return updateRequestMapper.toRequest(loadPort.loadById(id));
    }
}
