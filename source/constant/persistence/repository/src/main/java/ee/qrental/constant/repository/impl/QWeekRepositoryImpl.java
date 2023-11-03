package ee.qrental.constant.repository.impl;

import ee.qrental.constant.adapter.repository.QWeekRepository;
import ee.qrental.constant.entity.jakarta.QWeekJakartaEntity;
import ee.qrental.constant.repository.spring.QWeekSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QWeekRepositoryImpl implements QWeekRepository {

    private final QWeekSpringDataRepository springDataRepository;

    @Override
    public List<QWeekJakartaEntity> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public QWeekJakartaEntity save(final QWeekJakartaEntity entity) {
        return springDataRepository.save(entity);
    }

    @Override
    public QWeekJakartaEntity getReferenceById(final Long id) {
        return springDataRepository.getReferenceById(id);
    }

    @Override
    public void deleteById(final Long id) {
        springDataRepository.deleteById(id);
    }
}
