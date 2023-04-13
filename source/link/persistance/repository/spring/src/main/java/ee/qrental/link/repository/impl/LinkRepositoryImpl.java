package ee.qrental.link.repository.impl;



import ee.qrental.link.adapter.repository.LinkRepository;
import ee.qrental.link.entity.jakarta.LinkJakartaEntity;
import ee.qrental.link.repository.spring.LinkSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LinkRepositoryImpl implements LinkRepository {

    private final LinkSpringDataRepository springDataRepository;

    @Override
    public List<LinkJakartaEntity> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public LinkJakartaEntity save(final LinkJakartaEntity entity) {
        return springDataRepository.save(entity);
    }

    @Override
    public LinkJakartaEntity getReferenceById(final Long id) {
        return springDataRepository.getReferenceById(id);
    }

    @Override
    public void deleteById(final Long id) {
        springDataRepository.deleteById(id);
    }
}
