package ee.qrental.transaction.core.mapper.campaign;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.transaction.api.in.response.campaign.CampaignResponse;
import ee.qrental.transaction.domain.campaign.Campaign;

import static java.lang.String.format;

public class CampaignResponseMapper
    implements ResponseMapper<CampaignResponse, Campaign> {

  @Override
  public CampaignResponse toResponse(final Campaign domain) {

    return CampaignResponse.builder()
            .id(domain.getId())
            .campaign(domain.getCampaign())
            .description(domain.getDescription())
            .active(domain.getActive())
            .build();
  }

  @Override
  public String toObjectInfo(Campaign domain) {
    return format("Campaign : %s ", domain.getCampaign());
  }
}
