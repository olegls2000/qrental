package ee.qrental.bonus.repository.impl;

import ee.qrental.bonus.adapter.repository.WeekObligationRepository;
import ee.qrental.bonus.entity.jakarta.WeekObligationJakartaEntity;

import ee.qrental.bonus.repository.spring.WeekObligationSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WeekObligationRepositoryImpl implements WeekObligationRepository {

    private final WeekObligationSpringDataRepository springDataRepository;

    @Override
    public List<WeekObligationJakartaEntity> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public WeekObligationJakartaEntity save(final WeekObligationJakartaEntity entity) {
        return springDataRepository.save(entity);
    }

    @Override
    public WeekObligationJakartaEntity getReferenceById(final Long id) {
        return springDataRepository.getReferenceById(id);
    }

    @Override
    public void deleteById(final Long id) {
        springDataRepository.deleteById(id);
    }
}
