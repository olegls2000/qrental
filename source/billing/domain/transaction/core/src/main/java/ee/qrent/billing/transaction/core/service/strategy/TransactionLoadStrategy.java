package ee.qrent.billing.transaction.core.service.strategy;

import ee.qrent.billing.transaction.api.in.query.filter.DriverAndYearAndWeekAndFeeFilter;
import ee.qrent.billing.transaction.domain.Transaction;
import java.util.List;

public interface TransactionLoadStrategy {
    boolean canApply(final DriverAndYearAndWeekAndFeeFilter request);

    List<Transaction> load(final DriverAndYearAndWeekAndFeeFilter request);
}
