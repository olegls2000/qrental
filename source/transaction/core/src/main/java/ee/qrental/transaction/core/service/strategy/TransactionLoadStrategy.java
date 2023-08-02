package ee.qrental.transaction.core.service.strategy;

import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import ee.qrental.transaction.domain.Transaction;
import java.util.List;

public interface TransactionLoadStrategy {
    boolean canApply(final YearAndWeekAndDriverAndFeeFilter request);

    List<Transaction> load(final YearAndWeekAndDriverAndFeeFilter request);
}
