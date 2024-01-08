package ee.qrental.transaction.api.in.query.campaign;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.transaction.api.in.request.campaign.CampaignUpdateRequest;
import ee.qrental.transaction.api.in.response.campaign.CampaignResponse;

import java.util.List;

public interface GetCampaignQuery
    extends BaseGetQuery<CampaignUpdateRequest, CampaignResponse> {

  CampaignResponse getByCampaign(final String campaign);

  List<CampaignResponse> getActive();


}
