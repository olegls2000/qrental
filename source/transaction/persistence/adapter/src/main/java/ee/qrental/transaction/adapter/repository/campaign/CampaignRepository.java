package ee.qrental.transaction.adapter.repository.campaign;

import ee.qrental.transaction.entity.jakarta.campaign.CampaignJakartaEntity;

import java.util.List;

public interface CampaignRepository {
  CampaignJakartaEntity save(final CampaignJakartaEntity entity);

  void deleteById(final Long id);

  List<CampaignJakartaEntity> findAll();

  CampaignJakartaEntity getReferenceById(final Long id);

  CampaignJakartaEntity findByName(final String name);


}
