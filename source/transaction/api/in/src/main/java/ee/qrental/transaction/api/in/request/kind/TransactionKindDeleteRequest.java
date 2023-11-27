package ee.qrental.transaction.api.in.request.kind;

import ee.qrental.common.core.in.request.AbstractDeleteRequest;

public class TransactionKindDeleteRequest extends AbstractDeleteRequest {
    public TransactionKindDeleteRequest(final Long id) {
        super(id);
    }
}