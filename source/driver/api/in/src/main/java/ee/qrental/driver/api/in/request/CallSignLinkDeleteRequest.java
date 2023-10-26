package ee.qrental.driver.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

//TODO: Change to Stop Request, add Driver ID to optimise Controller!!

public class CallSignLinkDeleteRequest extends AbstractDeleteRequest {
    public CallSignLinkDeleteRequest(final Long id) {
        super(id);
    }
}