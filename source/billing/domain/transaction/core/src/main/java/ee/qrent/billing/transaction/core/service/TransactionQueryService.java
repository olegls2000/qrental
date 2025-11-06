package ee.qrent.billing.transaction.core.service;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.query.filter.*;
import ee.qrent.billing.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.core.mapper.TransactionResponseMapper;
import ee.qrent.billing.transaction.core.mapper.TransactionUpdateRequestMapper;
import ee.qrent.billing.transaction.core.service.strategy.TransactionLoadStrategy;
import ee.qrent.billing.transaction.domain.Transaction;

import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionQueryService implements GetTransactionQuery {

  private final TransactionLoadPort transactionLoadPort;
  private final List<TransactionLoadStrategy> loadStrategies;
  private final TransactionResponseMapper responseMapper;
  private final TransactionUpdateRequestMapper updateRequestMapper;
  private final GetQWeekQuery qWeekQuery;

  @Override
  public List<TransactionResponse> getAll() {
    return mapToTransactionResponseList(transactionLoadPort.loadAll());
  }

  @Override
  public TransactionResponse getById(final Long id) {

    return responseMapper.toResponse(transactionLoadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {

    return responseMapper.toObjectInfo(transactionLoadPort.loadById(id));
  }

  @Override
  public List<TransactionResponse> getAllByDriverId(final Long driverId) {

    return mapToTransactionResponseList(transactionLoadPort.loadAllByDriverId(driverId));
  }

  @Override
  public List<TransactionResponse> getAllByDriverIdAndQWeekId(
      final Long driverId, final Long qWeekId) {
    final var qWeek = qWeekQuery.getById(qWeekId);

    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByDriverIdAndBetweenDates(
            driverId, qWeek.getStart(), qWeek.getEnd()));
  }

  @Override
  public List<TransactionResponse> getAllByIds(List<Long> ids) {
    return mapToTransactionResponseList(transactionLoadPort.loadAllByIds(ids));
  }

  @Override
  public List<TransactionResponse> getAllByRentCalculationId(final Long rentCalculationId) {

    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByRentCalculationId(rentCalculationId));
  }

  @Override
  public List<TransactionResponse> getAllByBonusCalculationId(final Long bonusCalculationId) {

    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByBonusCalculationId(bonusCalculationId));
  }

  @Override
  public List<TransactionResponse> getAllByInsuranceCalculationId(
      final Long insuranceCalculationId) {
    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByInsuranceCalculationId(insuranceCalculationId));
  }

  @Override
  public List<TransactionResponse> getAllByInsuranceCaseId(final Long insuranceCaseId) {
    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByInsuranceCaseId(insuranceCaseId));
  }

  @Override
  public List<TransactionResponse> getAllByFilter(final DriverAndYearAndWeekAndFeeFilter filter) {

    return mapToTransactionResponseList(
        loadStrategies.stream()
            .filter(strategy -> strategy.canApply(filter))
            .findFirst()
            .orElseThrow(
                () -> new RuntimeException("No Load Strategy was found for the request: " + filter))
            .load(filter));
  }

  @Override
  public List<TransactionResponse> getAllByFilter(final DriverAndPeriodAndKindCodesFilter filter) {

    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByDriverIdAndBetweenDatesAndKindCodes(
            filter.getDriverId(),
            filter.getDateStart(),
            filter.getDateEnd(),
            filter.getKindCodes()));
  }

  @Override
  public List<TransactionResponse> getAllByFilter(final PeriodFilter filter) {
    return mapToTransactionResponseList(
        transactionLoadPort.loadAllBetweenDates(filter.getDateStart(), filter.getDateEnd()));
  }

  @Override
  public List<TransactionResponse> getAllByFilter(final DriverAndPeriodFilter filter) {
    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByDriverIdAndBetweenDates(
            filter.getDriverId(), filter.getDateStart(), filter.getDateEnd()));
  }

  @Override
  public List<TransactionResponse> getAllByFilter(final DriverAndPeriodAndTypeCodesFilter filter) {

    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByDriverIdAndBetweenDatesAndTypeCodes(
            filter.getDriverId(),
            filter.getDateStart(),
            filter.getDateEnd(),
            filter.getTypeCodes()));
  }

  @Override
  public List<TransactionResponse> getAllByFilter(final DriverAndQWeekIntervalFilter filter) {
    final var intervalStartDate = qWeekQuery.getStartDateOrFirstDate(filter.getStartQWeekId());
    final var intervalEndDate = qWeekQuery.getEndDateOrCurrent(filter.getEndQWeekId());

    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByDriverIdAndBetweenDates(
            filter.getDriverId(), intervalStartDate, intervalEndDate));
  }

  @Override
  public List<TransactionResponse> getAllByQWeekId(final Long qWeekId) {
    final var qWeek = qWeekQuery.getById(qWeekId);
    final var periodFilter =
        PeriodFilter.builder().dateStart(qWeek.getStart()).dateEnd(qWeek.getEnd()).build();

    return getAllByFilter(periodFilter);
  }

  @Override
  public TransactionUpdateRequest getUpdateRequestById(final Long id) {
    return updateRequestMapper.toRequest(transactionLoadPort.loadById(id));
  }

  private List<TransactionResponse> mapToTransactionResponseList(
      final List<Transaction> transactions) {
    return transactions.stream()
        .map(responseMapper::toResponse)
        .sorted(comparing(TransactionResponse::getDate).reversed())
        .collect(toList());
  }
}
