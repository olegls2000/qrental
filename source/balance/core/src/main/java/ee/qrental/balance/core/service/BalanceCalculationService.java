package ee.qrental.balance.core.service;

import static java.util.stream.Collectors.*;

import ee.qrental.balance.api.in.request.BalanceCalculationAddRequest;
import ee.qrental.balance.api.in.usecase.BalanceCalculationAddUseCase;
import ee.qrental.balance.api.out.BalanceCalculationAddPort;
import ee.qrental.balance.core.mapper.BalanceCalculationAddRequestMapper;
import ee.qrental.balance.core.validator.BalanceCalculationBusinessRuleValidator;
import ee.qrental.balance.domain.Balance;
import ee.qrental.balance.domain.BalanceCalculationResult;
import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.query.filter.PeriodFilter;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceCalculationService implements BalanceCalculationAddUseCase {

  private final BalanceCalculationPeriodService balanceCalculationPeriodService;
  private final BalanceCalculationAddRequestMapper addRequestMapper;
  private final BalanceCalculationBusinessRuleValidator businessRuleValidator;
  private final BalanceCalculationAddPort addPort;
  private final GetTransactionQuery transactionQuery;

  @Transactional
  @Override
  public void add(final BalanceCalculationAddRequest addRequest) {
    final var domain = addRequestMapper.toDomain(addRequest);
    final var actionDate = addRequest.getActionDate();
    final var weekIterator = balanceCalculationPeriodService.getWeekIterator(actionDate);
    while (weekIterator.hasNext()) {
      final var week = weekIterator.next();
      final var filter = PeriodFilter.builder().dateStart(week.start()).datEnd(week.end()).build();
      final var weekTransactions = transactionQuery.getAllByFilter(filter);
      final var driverVsTransaction =
          weekTransactions.stream().collect(groupingBy(TransactionResponse::getDriverId));
      for (Map.Entry<Long, List<TransactionResponse>> entry : driverVsTransaction.entrySet()) {
        final var driversTransactions = entry.getValue();
        final var amount =
            driversTransactions.stream()
                .map(TransactionResponse::getRealAmount)
                .reduce(BigDecimal::add)
                .get();
        final var driverId = entry.getKey();
        final var balance =
            Balance.builder()
                .driverId(driverId)
                .weekNumber(week.weekNumber())
                .year(week.getYear())
                .created(LocalDate.now())
                .amount(amount)
                .build();

        final var transactionIds =
            driversTransactions.stream().map(TransactionResponse::getId).collect(toSet());
        final var result =
            BalanceCalculationResult.builder()
                .balance(balance)
                .transactionIds(transactionIds)
                .build();
        domain.getResults().add(result);
      }
    }
    addPort.add(domain);
  }
}
