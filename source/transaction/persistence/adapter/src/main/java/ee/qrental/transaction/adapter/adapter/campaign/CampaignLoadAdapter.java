package ee.qrental.transaction.adapter.adapter.campaign;

import ee.qrental.transaction.adapter.mapper.campaign.CampaignAdapterMapper;
import ee.qrental.transaction.adapter.repository.campaign.CampaignRepository;
import ee.qrental.transaction.api.out.campaign.CampaignLoadPort;
import ee.qrental.transaction.domain.campaign.Campaign;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class CampaignLoadAdapter implements CampaignLoadPort {

  private final CampaignRepository repository;
  private final CampaignAdapterMapper mapper;

  @Override
  public List<Campaign> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Campaign loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public Campaign loadByCampaign(String name) {
    return mapper.mapToDomain(repository.findByName(name));
  }


}
