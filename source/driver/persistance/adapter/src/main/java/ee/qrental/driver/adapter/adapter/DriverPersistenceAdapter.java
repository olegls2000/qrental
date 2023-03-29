package ee.qrental.driver.adapter.adapter;

import ee.qrental.driver.adapter.mapper.DriverAdapterMapper;
import ee.qrental.driver.adapter.repository.DriverRepository;
import ee.qrental.driver.api.out.DriverAddPort;
import ee.qrental.driver.api.out.DriverDeletePort;
import ee.qrental.driver.api.out.DriverUpdatePort;
import ee.qrental.driver.domain.Driver;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DriverPersistenceAdapter
        implements DriverAddPort,
        DriverUpdatePort,
        DriverDeletePort {

    private final DriverRepository repository;
    private final DriverAdapterMapper mapper;

    @Override
    public Driver add(final Driver driver) {
        return mapper.mapToDomain(
                repository.save(
                        mapper.mapToEntity(driver)
                ));
    }

    @Override
    public Driver update(final Driver driver) {
        return mapper.mapToDomain(
                repository.save(
                        mapper.mapToEntity(driver)
                ));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
