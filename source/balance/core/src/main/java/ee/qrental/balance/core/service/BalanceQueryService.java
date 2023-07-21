package ee.qrental.balance.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.balance.api.in.query.GetBalanceQuery;
import ee.qrental.balance.api.in.response.BalanceResponse;
import ee.qrental.balance.api.out.BalanceLoadPort;
import ee.qrental.balance.core.mapper.BalanceResponseMapper;
import ee.qrental.balance.domain.Balance;
import ee.qrental.common.core.utils.QTimeUtils;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodAndDriverFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceQueryService implements GetBalanceQuery {

  private final BalanceLoadPort loadPort;
  private final BalanceResponseMapper mapper;
  private final GetTransactionQuery transactionQuery;

  @Override
  public List<BalanceResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public BalanceResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public BalanceResponse getByDriverIdAndYearAndWeekNumber(
      final Long driverId, final Integer year, final Integer weekNumber) {
    return mapper.toResponse(
        loadPort.loadByDriverIdAndYearAndWeekNumber(driverId, year, weekNumber));
  }

  @Override
  public BigDecimal getRawBalanceTotalByDriver(final Long driverId) {
    final var latestBalance = loadPort.loadLatestByDriver(driverId);
    final var balanceSum = latestBalance != null ? latestBalance.getAmount() : BigDecimal.ZERO;
    final var rawSum = getRawTransactionsSum(latestBalance, driverId);

    return balanceSum.add(rawSum);
  }

  private BigDecimal getRawTransactionsSum(final Balance latestBalance, final Long driverId) {
    final var latestBalanceYear = latestBalance != null ? latestBalance.getYear() : 2023;
    final var latestBalanceWeek = latestBalance != null ? latestBalance.getWeekNumber() : 1;
    final var latestBalanceEndOfWeekDay =
        QTimeUtils.getLastDayOfWeekInYear(latestBalanceYear, latestBalanceWeek);
    final var rawTransactionFilter =
        PeriodAndDriverFilter.builder()
            .driverId(driverId)
            .dateStart(latestBalanceEndOfWeekDay)
            .datEnd(LocalDate.now())
            .build();
    final var rawTransactions = transactionQuery.getAllByFilter(rawTransactionFilter);
    final var result =
        rawTransactions.stream()
            .map(TransactionResponse::getRealAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

    return result;
  }

  @Override
  public BigDecimal getFeeByDriver(Long driverId) {
    final var latestBalance = loadPort.loadLatestByDriver(driverId);
    final var fee =latestBalance!= null?  latestBalance.getFee() : BigDecimal.ZERO;

    return fee;
  }
}
