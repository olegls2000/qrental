package ee.qrent.billing.transaction.api.out;

import ee.qrent.common.out.port.AddPort;
import ee.qrent.billing.transaction.domain.Transaction;

public interface TransactionAddPort extends AddPort<Transaction> {}
