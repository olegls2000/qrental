package ee.qrental.transaction.adapter.mapper.campaign;

import ee.qrental.transaction.domain.campaign.Campaign;
import ee.qrental.transaction.entity.jakarta.campaign.CampaignJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CampaignAdapterMapper {



    public Campaign mapToDomain(final CampaignJakartaEntity entity) {

        return Campaign.builder()
                .id(entity.getId())
                .campaign(entity.getCampaign())
                .description(entity.getDescription())
                .active(entity.getActive())
                .build();
    }

    public CampaignJakartaEntity mapToEntity(final Campaign domain) {

        return CampaignJakartaEntity.builder()
                .id(domain.getId())
                .campaign(domain.getCampaign())
                .description(domain.getDescription())
                .active(domain.getActive())
                .build();
    }
}
