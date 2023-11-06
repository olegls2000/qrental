package ee.qrental.transaction.api.in.query;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.query.filter.PeriodFilter;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import ee.qrental.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.util.List;

public interface GetTransactionQuery
    extends BaseGetQuery<TransactionUpdateRequest, TransactionResponse> {

  List<TransactionResponse> getAllByDriverId(final Long driverId);

  List<TransactionResponse> getAllByIds(final List<Long> ids);

  List<TransactionResponse> getAllByRentCalculationId(final Long rentCalculationId);

  List<TransactionResponse> getAllByFilter(final YearAndWeekAndDriverAndFeeFilter filter);

  List<TransactionResponse> getAllByFilter(final PeriodAndDriverFilter filter);

  List<TransactionResponse> getAllByFilter(final PeriodFilter filter);
}
