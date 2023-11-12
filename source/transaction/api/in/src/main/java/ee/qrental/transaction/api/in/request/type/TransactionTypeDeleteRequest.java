package ee.qrental.transaction.api.in.request.type;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class TransactionTypeDeleteRequest extends AbstractDeleteRequest {
    public TransactionTypeDeleteRequest(final Long id) {
        super(id);
    }
}