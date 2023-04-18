package ee.qrental.firm.repository.impl;

import ee.qrental.firm.adapter.repository.FirmRepository;
import ee.qrental.firm.entity.jakarta.FirmJakartaEntity;
import ee.qrental.firm.repository.spring.FirmSpringDataRepository;
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
