package ee.qrental.transaction.core.service;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.query.filter.PeriodFilter;
import ee.qrental.transaction.api.in.query.filter.YearAndWeekAndDriverAndFeeFilter;
import ee.qrental.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.core.mapper.TransactionResponseMapper;
import ee.qrental.transaction.core.mapper.TransactionUpdateRequestMapper;
import ee.qrental.transaction.core.service.strategy.TransactionLoadStrategy;
import ee.qrental.transaction.domain.Transaction;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionQueryService implements GetTransactionQuery {

  private final TransactionLoadPort transactionLoadPort;
  private final List<TransactionLoadStrategy> loadStrategies;
  private final TransactionResponseMapper responseMapper;
  private final TransactionUpdateRequestMapper updateRequestMapper;

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
  public List<TransactionResponse> getAllByCalculationId(final Long calculationId) {
    return mapToTransactionResponseList(transactionLoadPort.loadAllByCalculationId(calculationId));
  }

  @Override
  public List<TransactionResponse> getAllByFilter(final YearAndWeekAndDriverAndFeeFilter filter) {
    return mapToTransactionResponseList(
        loadStrategies.stream()
            .filter(strategy -> strategy.canApply(filter))
            .findFirst()
            .orElseThrow(
                () -> new RuntimeException("No Load Strategy was found for the request: " + filter))
            .load(filter));
  }

  @Override
  public List<TransactionResponse> getAllByFilter(final PeriodAndDriverFilter filter) {
    return mapToTransactionResponseList(
        transactionLoadPort.loadAllByDriverIdAndBetweenDays(
            filter.getDriverId(), filter.getDateStart(), filter.getDateEnd()));
  }

  @Override
  public List<TransactionResponse> getAllByFilter(final PeriodFilter filter) {
    return mapToTransactionResponseList(
        transactionLoadPort.loadAllBetweenDays(filter.getDateStart(), filter.getDatEnd()));
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
