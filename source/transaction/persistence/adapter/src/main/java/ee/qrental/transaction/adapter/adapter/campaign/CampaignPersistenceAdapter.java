package ee.qrental.transaction.adapter.adapter.campaign;

import ee.qrental.transaction.adapter.mapper.campaign.CampaignAdapterMapper;
import ee.qrental.transaction.adapter.repository.campaign.CampaignRepository;
import ee.qrental.transaction.api.out.campaign.CampaignAddPort;
import ee.qrental.transaction.api.out.campaign.CampaignDeletePort;
import ee.qrental.transaction.api.out.campaign.CampaignUpdatePort;
import ee.qrental.transaction.domain.campaign.Campaign;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CampaignPersistenceAdapter
    implements CampaignAddPort, CampaignUpdatePort, CampaignDeletePort {

  private final CampaignRepository repository;
  private final CampaignAdapterMapper mapper;

  @Override
  public Campaign add(final Campaign Campaign) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(Campaign)));
  }

  @Override
  public Campaign update(final Campaign Campaign) {
    return mapper.mapToDomain(repository.save(mapper.mapToEntity(Campaign)));
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
