package ee.qrental.driver.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class CallSignLinkDeleteRequest extends AbstractDeleteRequest {
    public CallSignLinkDeleteRequest(final Long id) {
        super(id);
    }
}