package ee.qrental.transaction.spring.config.campaign;

import ee.qrental.transaction.api.in.query.campaign.GetCampaignQuery;
import ee.qrental.transaction.api.out.campaign.CampaignAddPort;
import ee.qrental.transaction.api.out.campaign.CampaignDeletePort;
import ee.qrental.transaction.api.out.campaign.CampaignLoadPort;
import ee.qrental.transaction.api.out.campaign.CampaignUpdatePort;
import ee.qrental.transaction.core.mapper.campaign.CampaignAddRequestMapper;
import ee.qrental.transaction.core.mapper.campaign.CampaignResponseMapper;
import ee.qrental.transaction.core.mapper.campaign.CampaignUpdateRequestMapper;
import ee.qrental.transaction.core.service.campaign.CampaignQueryService;
import ee.qrental.transaction.core.service.campaign.CampaignUseCaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CampaignServiceConfig {

    @Bean
    public GetCampaignQuery getCampaignQueryService(
            final CampaignLoadPort loadPort,
            final CampaignResponseMapper mapper,
            final CampaignUpdateRequestMapper updateRequestMapper) {
        return new CampaignQueryService(loadPort, mapper, updateRequestMapper);
    }

    @Bean
    public CampaignUseCaseService getCampaignUseCaseService(
            final CampaignAddPort addPort,
            final CampaignUpdatePort updatePort,
            final CampaignDeletePort deletePort,
            final CampaignLoadPort loadPort,
            final CampaignAddRequestMapper addRequestMapper,
            final CampaignUpdateRequestMapper updateRequestMapper) {
        return new CampaignUseCaseService(
                addPort,
                updatePort,
                deletePort,
                loadPort,
                addRequestMapper,
                updateRequestMapper
        );
    }
}
