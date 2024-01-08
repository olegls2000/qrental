package ee.qrental.transaction.core.mapper.campaign;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.transaction.api.in.request.campaign.CampaignUpdateRequest;
import ee.qrental.transaction.domain.campaign.Campaign;

public class CampaignUpdateRequestMapper
        implements UpdateRequestMapper<CampaignUpdateRequest, Campaign> {

    @Override
    public Campaign toDomain(final CampaignUpdateRequest request) {
        return Campaign.builder()
                .id(request.getId())
                .campaign(request.getCampaign())
                .description(request.getDescription())
                .active(request.getActive())
                .build();
    }

    @Override
    public CampaignUpdateRequest toRequest(final Campaign domain) {
        return CampaignUpdateRequest.builder()
                .id(domain.getId())
                .campaign(domain.getCampaign())
                .description(domain.getDescription())
                .active(domain.getActive())
                .build();
    }
}
