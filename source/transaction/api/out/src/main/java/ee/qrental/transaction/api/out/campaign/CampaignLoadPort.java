package ee.qrental.transaction.api.out.campaign;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.transaction.domain.campaign.Campaign;

public interface CampaignLoadPort extends LoadPort<Campaign> {

  Campaign loadByCampaign(final String name);


}
