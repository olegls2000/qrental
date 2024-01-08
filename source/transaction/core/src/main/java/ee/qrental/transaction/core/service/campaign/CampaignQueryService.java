package ee.qrental.transaction.core.service.campaign;

import ee.qrental.transaction.api.in.query.campaign.GetCampaignQuery;
import ee.qrental.transaction.api.in.request.campaign.CampaignUpdateRequest;
import ee.qrental.transaction.api.in.response.campaign.CampaignResponse;
import ee.qrental.transaction.api.out.campaign.CampaignLoadPort;
import ee.qrental.transaction.core.mapper.campaign.CampaignResponseMapper;
import ee.qrental.transaction.core.mapper.campaign.CampaignUpdateRequestMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CampaignQueryService implements GetCampaignQuery {

  private final CampaignLoadPort loadPort;
  private final CampaignResponseMapper mapper;
  private final CampaignUpdateRequestMapper updateRequestMapper;


  @Override
  public List<CampaignResponse> getAll() {
    return null;
  }

  @Override
  public CampaignResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public CampaignUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }


  @Override
  public CampaignResponse getByCampaign(String campaign) {
    return null;
  }

  @Override
  public List<CampaignResponse> getActive() {
    return null;
  }
}
