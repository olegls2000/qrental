package ee.qrental.transaction.spring.config.campaign;

import ee.qrental.transaction.adapter.repository.campaign.CampaignRepository;
import ee.qrental.transaction.repository.impl.campaign.CampaignRepositoryImpl;
import ee.qrental.transaction.repository.spring.campaign.CampaignSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CampaignRepositoryConfig {

  @Bean
  CampaignRepository getCampaignRepository(
      final CampaignSpringDataRepository springDataRepository) {
    return new CampaignRepositoryImpl(springDataRepository);
  }
}
