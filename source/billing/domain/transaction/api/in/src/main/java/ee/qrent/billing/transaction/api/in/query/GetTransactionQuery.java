package ee.qrent.billing.transaction.api.in.query;

import ee.qrent.billing.transaction.api.in.query.filter.*;
import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import java.util.List;

public interface GetTransactionQuery
    extends BaseGetQuery<TransactionUpdateRequest, TransactionResponse> {

  List<TransactionResponse> getAllByIds(final List<Long> ids);

  List<TransactionResponse> getAllByDriverId(final Long driverId);

  List<TransactionResponse> getAllByQWeekId(final Long qWeekId);

  List<TransactionResponse> getAllByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  List<TransactionResponse> getAllByFilter(final PeriodFilter filter);

  List<TransactionResponse> getAllByFilter(final DriverAndYearAndWeekAndFeeFilter filter);

  List<TransactionResponse> getAllByFilter(final DriverAndPeriodAndKindFilter filter);

  List<TransactionResponse> getAllByFilter(final DriverAndPeriodAndTypeCodesFilter filter);

  List<TransactionResponse> getAllByFilter(final DriverAndQWeekIntervalFilter filter);

  List<TransactionResponse> getAllByRentCalculationId(final Long rentCalculationId);

  List<TransactionResponse> getAllByBonusCalculationId(final Long bonusCalculationId);

  List<TransactionResponse> getAllByInsuranceCalculationId(final Long insuranceCalculationId);

  List<TransactionResponse> getAllByInsuranceCaseId(final Long insuranceCaseId);
}
