package ee.qrental.transaction.core.service.balance;

import static java.util.stream.Collectors.toList;

import ee.qrental.driver.api.in.query.GetDriverQuery;
import ee.qrental.driver.api.in.response.DriverResponse;
import ee.qrental.transaction.api.in.query.GetDriverBalanceQuery;
import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.domain.Transaction;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalancesQueryService implements GetDriverBalanceQuery {

  private final TransactionLoadPort transactionLoadPort;
  private final GetDriverQuery driverQuery;

  @Override
  public List<BalanceResponse> getAll() {
    return driverQuery.getAll().stream().map(this::getBalanceByDriverId).collect(toList());
  }

  @Override
  public BigDecimal getTotalByDriverId(final Long driverId) {
    return calculateTotal(transactionLoadPort.loadAllByDriverId(driverId));
  }

  private BalanceResponse getBalanceByDriverId(final DriverResponse driver) {
    final var driverId = driver.getId();
    final var total = calculateTotal(transactionLoadPort.loadAllByDriverId(driverId));

    return BalanceResponse.builder()
        .driverId(driverId)
        .total(total)
        .firstName(driver.getFirstName())
        .lastName(driver.getLastName())
        .build();
  }

  private BigDecimal calculateTotal(final List<Transaction> transactions) {
    return transactions.stream()
        .map(Transaction::getRealAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
