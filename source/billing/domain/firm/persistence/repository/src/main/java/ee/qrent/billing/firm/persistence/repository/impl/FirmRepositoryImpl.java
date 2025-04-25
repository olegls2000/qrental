package ee.qrent.billing.firm.persistence.repository.impl;

import ee.qrent.billing.firm.persistence.repository.FirmRepository;
import ee.qrent.billing.firm.persistence.entity.jakarta.FirmJakartaEntity;
import ee.qrent.billing.firm.persistence.repository.spring.FirmSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FirmRepositoryImpl implements FirmRepository {

    private final FirmSpringDataRepository springDataRepository;

    @Override
    public List<FirmJakartaEntity> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public FirmJakartaEntity save(final FirmJakartaEntity entity) {
        return springDataRepository.save(entity);
    }

    @Override
    public FirmJakartaEntity getReferenceById(final Long id) {
        return springDataRepository.getReferenceById(id);
    }

    @Override
    public void deleteById(final Long id) {
        springDataRepository.deleteById(id);
    }
}
