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
    return driverQuery.getAll().stream()
        .map(this::getBalanceByDriverId)
        .sorted(this::getBalanceComparator)
        .collect(toList());
  }

  private int getBalanceComparator(BalanceResponse bal1, BalanceResponse bal2) {
    final var callSign1 = bal1.getCallSign();
    final var callSign2 = bal2.getCallSign();
    if (callSign1 != null && callSign2 != null) {
      return callSign1 - callSign2;
    }
    return 0;
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
        .callSign(driver.getCallSign())
        .firstName(driver.getFirstName())
        .lastName(driver.getLastName())
        .phone(driver.getPhone())
        .build();
  }

  private BigDecimal calculateTotal(final List<Transaction> transactions) {
    return transactions.stream()
        .map(Transaction::getRealAmount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
