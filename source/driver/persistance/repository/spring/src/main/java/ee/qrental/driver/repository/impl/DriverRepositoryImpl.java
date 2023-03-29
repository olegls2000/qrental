package ee.qrental.driver.repository.impl;



import ee.qrental.driver.adapter.repository.DriverRepository;
import ee.qrental.driver.entity.jakarta.DriverJakartaEntity;
import ee.qrental.driver.repository.spring.DriveSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DriverRepositoryImpl implements DriverRepository {

    private final DriveSpringDataRepository springDataRepository;

    @Override
    public List<DriverJakartaEntity> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public DriverJakartaEntity save(final DriverJakartaEntity jpaEntity) {
        return springDataRepository.save(jpaEntity);
    }

    @Override
    public DriverJakartaEntity getReferenceById(final Long id) {
        return springDataRepository.getReferenceById(id);
    }

    @Override
    public void deleteById(final Long id) {
        springDataRepository.deleteById(id);
    }
}
