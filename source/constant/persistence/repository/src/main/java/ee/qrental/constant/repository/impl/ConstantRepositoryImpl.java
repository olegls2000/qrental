package ee.qrental.constant.repository.impl;

import ee.qrental.constant.adapter.repository.ConstantRepository;
import ee.qrental.constant.entity.jakarta.ConstantJakartaEntity;
import ee.qrental.constant.repository.spring.ConstantSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ConstantRepositoryImpl implements ConstantRepository {

    private final ConstantSpringDataRepository springDataRepository;

    @Override
    public List<ConstantJakartaEntity> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public ConstantJakartaEntity save(final ConstantJakartaEntity entity) {
        return springDataRepository.save(entity);
    }

    @Override
    public ConstantJakartaEntity getReferenceById(final Long id) {
        return springDataRepository.getReferenceById(id);
    }

    @Override
    public ConstantJakartaEntity findByName(final String name) {
        return springDataRepository.findByConstant(name);
    }

    @Override
    public void deleteById(final Long id) {
        springDataRepository.deleteById(id);
    }
}
