package ee.qrental.transaction.api.in.request.campaign;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class CampaignDeleteRequest extends AbstractDeleteRequest {
    public CampaignDeleteRequest(final Long id) {
        super(id);
    }
}