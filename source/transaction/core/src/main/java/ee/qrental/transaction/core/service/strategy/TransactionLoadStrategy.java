package ee.qrental.transaction.core.service.strategy;

import ee.qrental.transaction.api.in.request.TransactionFilterRequest;
import ee.qrental.transaction.domain.Transaction;
import java.util.List;

public interface TransactionLoadStrategy {
    boolean canApply(final TransactionFilterRequest request);

    List<Transaction> load(final TransactionFilterRequest request);
}
