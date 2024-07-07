package ee.qrental.transaction.api.in.request;

import ee.qrent.common.in.request.AbstractDeleteRequest;

public class TransactionDeleteRequest extends AbstractDeleteRequest {
    public TransactionDeleteRequest( Long id) {
        super(id);
    }
}