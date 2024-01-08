package ee.qrental.transaction.spring.config.campaign;

import ee.qrental.transaction.core.mapper.campaign.CampaignAddRequestMapper;
import ee.qrental.transaction.core.mapper.campaign.CampaignResponseMapper;
import ee.qrental.transaction.core.mapper.campaign.CampaignUpdateRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CampaignMapperConfig {
  @Bean
  CampaignAddRequestMapper getCampaignAddRequestMapper() {
    return new CampaignAddRequestMapper();
  }

  @Bean
  CampaignResponseMapper getCampaignResponseMapper() {
    return new CampaignResponseMapper();
  }

  @Bean
  CampaignUpdateRequestMapper getCampaignUpdateRequestMapper() {
    return new CampaignUpdateRequestMapper();
  }
}
