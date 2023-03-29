package ee.qrental.driver.core.service;


import ee.qrental.driver.api.in.request.DriverAddRequest;
import ee.qrental.driver.api.in.request.DriverDeleteRequest;
import ee.qrental.driver.api.in.request.DriverUpdateRequest;
import ee.qrental.driver.api.in.usecase.DriverAddUseCase;
import ee.qrental.driver.api.in.usecase.DriverDeleteUseCase;
import ee.qrental.driver.api.in.usecase.DriverUpdateUseCase;
import ee.qrental.driver.api.out.DriverAddPort;
import ee.qrental.driver.api.out.DriverDeletePort;
import ee.qrental.driver.api.out.DriverLoadPort;
import ee.qrental.driver.api.out.DriverUpdatePort;
import ee.qrental.driver.core.mapper.DriverAddRequestMapper;
import ee.qrental.driver.core.mapper.DriverUpdateRequestMapper;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class DriverUseCaseService implements DriverAddUseCase, DriverUpdateUseCase, DriverDeleteUseCase {

    private final DriverAddPort addPort;
    private final DriverUpdatePort updatePort;
    private final DriverDeletePort deletePort;
    private final DriverLoadPort loadPort;
    private final DriverAddRequestMapper addRequestMapper;
    private final DriverUpdateRequestMapper updateRequestMapper;

    @Override
    public Long add(final DriverAddRequest request) {
        return addPort.add(addRequestMapper.toDomain(request)).getId();
    }

    @Override
    public void update(final DriverUpdateRequest request) {
        checkExistence(request.getId());
        updatePort.update(updateRequestMapper.toDomain(request));
    }

    @Override
    public void delete(final DriverDeleteRequest request) {
        deletePort.delete(request.getId());
    }

    private void checkExistence(final Long id) {
        if (loadPort.loadById(id) == null) {
            throw new RuntimeException("Update of Driver failed. No Record with id = " + id);
        }
    }
}
