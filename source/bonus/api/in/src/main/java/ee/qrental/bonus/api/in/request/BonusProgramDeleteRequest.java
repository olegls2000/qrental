package ee.qrental.bonus.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class BonusProgramDeleteRequest extends AbstractDeleteRequest {
    public BonusProgramDeleteRequest(final Long id) {
        super(id);
    }
}