package ee.qrent.billing.transaction.api.in.request.kind;

import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransactionKindDeleteRequest extends AbstractDeleteRequest {
    public TransactionKindDeleteRequest(final Long id) {
        super(id);
    }
}