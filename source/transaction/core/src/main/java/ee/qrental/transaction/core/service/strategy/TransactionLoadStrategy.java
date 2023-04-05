package ee.qrental.transaction.core.service.strategy;

import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverFilter;
import ee.qrental.transaction.domain.Transaction;
import java.util.List;

public interface TransactionLoadStrategy {
    boolean canApply(final YearAndWeekAndDriverFilter request);

    List<Transaction> load(final YearAndWeekAndDriverFilter request);
}
