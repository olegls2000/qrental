package ee.qrental.transaction.core.mapper.campaign;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.transaction.api.in.request.campaign.CampaignAddRequest;
import ee.qrental.transaction.domain.campaign.Campaign;

public class CampaignAddRequestMapper
        implements AddRequestMapper<CampaignAddRequest, Campaign> {

    @Override
    public Campaign toDomain(final CampaignAddRequest request) {
        return Campaign.builder()
                .id(null)
                .campaign(request.getCampaign())
                .description(request.getDescription())
                .active(request.getActive())
                .build();
    }
}
