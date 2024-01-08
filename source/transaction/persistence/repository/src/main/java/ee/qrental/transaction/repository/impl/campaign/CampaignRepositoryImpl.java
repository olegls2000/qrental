package ee.qrental.transaction.repository.impl.campaign;

import ee.qrental.transaction.adapter.repository.campaign.CampaignRepository;
import ee.qrental.transaction.entity.jakarta.campaign.CampaignJakartaEntity;
import ee.qrental.transaction.repository.spring.campaign.CampaignSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CampaignRepositoryImpl implements CampaignRepository {

    private final CampaignSpringDataRepository springDataRepository;

    @Override
    public List<CampaignJakartaEntity> findAll() {
        return springDataRepository.findAll();
    }

    @Override
    public CampaignJakartaEntity save(final CampaignJakartaEntity entity) {
        return springDataRepository.save(entity);
    }

    @Override
    public CampaignJakartaEntity getReferenceById(final Long id) {
        return springDataRepository.getReferenceById(id);
    }

    @Override
    public CampaignJakartaEntity findByName(final String name) {
        return springDataRepository.findByCampaign(name);
    }

    @Override
    public void deleteById(final Long id) {
        springDataRepository.deleteById(id);
    }
}
