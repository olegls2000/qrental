package ee.qrental.transaction.api.in.request;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class TransactionDeleteRequest extends AbstractDeleteRequest {
    public TransactionDeleteRequest( Long id) {
        super(id);
    }
}