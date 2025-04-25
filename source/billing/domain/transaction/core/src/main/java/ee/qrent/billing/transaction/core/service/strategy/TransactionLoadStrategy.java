package ee.qrent.billing.transaction.core.service.strategy;

import ee.qrent.billing.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import ee.qrent.billing.transaction.domain.Transaction;
import java.util.List;

public interface TransactionLoadStrategy {
    boolean canApply(final YearAndWeekAndDriverAndFeeFilter request);

    List<Transaction> load(final YearAndWeekAndDriverAndFeeFilter request);
}
