package ee.qrent.billing.transaction.api.in.request.type;

import ee.qrent.common.in.request.AbstractDeleteRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TransactionTypeDeleteRequest extends AbstractDeleteRequest {
    public TransactionTypeDeleteRequest(final Long id) {
        super(id);
    }
}