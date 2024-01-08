package ee.qrental.transaction.spring.config.campaign;

import ee.qrental.transaction.adapter.adapter.campaign.CampaignLoadAdapter;
import ee.qrental.transaction.adapter.adapter.campaign.CampaignPersistenceAdapter;
import ee.qrental.transaction.adapter.mapper.campaign.CampaignAdapterMapper;
import ee.qrental.transaction.adapter.repository.campaign.CampaignRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CampaignAdapterConfig {

  @Bean
  CampaignAdapterMapper getCampaignAdapterMapper() {
    return new CampaignAdapterMapper();
  }

  @Bean
  CampaignLoadAdapter getCampaignLoadAdapter(
      final CampaignRepository repository, final CampaignAdapterMapper mapper) {
    return new CampaignLoadAdapter(repository, mapper);
  }

  @Bean
  CampaignPersistenceAdapter getCampaignPersistenceAdapter(
      final CampaignRepository repository, final CampaignAdapterMapper mapper) {
    return new CampaignPersistenceAdapter(repository, mapper);
  }
}
