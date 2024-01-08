package ee.qrental.transaction.core.service.campaign;

import ee.qrental.transaction.api.in.request.campaign.CampaignAddRequest;
import ee.qrental.transaction.api.in.request.campaign.CampaignDeleteRequest;
import ee.qrental.transaction.api.in.request.campaign.CampaignUpdateRequest;
import ee.qrental.transaction.api.in.usecase.campaign.CampaignAddUseCase;
import ee.qrental.transaction.api.in.usecase.campaign.CampaignDeleteUseCase;
import ee.qrental.transaction.api.in.usecase.campaign.CampaignUpdateUseCase;
import ee.qrental.transaction.api.out.campaign.CampaignAddPort;
import ee.qrental.transaction.api.out.campaign.CampaignDeletePort;
import ee.qrental.transaction.api.out.campaign.CampaignLoadPort;
import ee.qrental.transaction.api.out.campaign.CampaignUpdatePort;
import ee.qrental.transaction.core.mapper.campaign.CampaignAddRequestMapper;
import ee.qrental.transaction.core.mapper.campaign.CampaignUpdateRequestMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CampaignUseCaseService
    implements CampaignAddUseCase,
        CampaignUpdateUseCase,
        CampaignDeleteUseCase {

  private final CampaignAddPort addPort;
  private final CampaignUpdatePort updatePort;
  private final CampaignDeletePort deletePort;
  private final CampaignLoadPort loadPort;
  private final CampaignAddRequestMapper addRequestMapper;
  private final CampaignUpdateRequestMapper updateRequestMapper;

  @Override
  public Long add(final CampaignAddRequest request) {

    return addPort.add(addRequestMapper.toDomain(request)).getId();
  }

  @Override
  public void update(final CampaignUpdateRequest request) {
    checkExistence(request.getId());

    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Override
  public void delete(final CampaignDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Campaign failed. No Record with id = " + id);
    }
  }
}
