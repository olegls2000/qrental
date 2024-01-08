package ee.qrental.transaction.repository.spring.campaign;

import ee.qrental.transaction.entity.jakarta.campaign.CampaignJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignSpringDataRepository
    extends JpaRepository<CampaignJakartaEntity, Long> {

  CampaignJakartaEntity findByCampaign(final String campaign);


}
