package ee.qrental.transaction.api.in.query.balance;

import ee.qrental.transaction.api.in.response.balance.BalanceRawContextResponse;
import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import java.math.BigDecimal;
import java.util.List;

public interface GetBalanceQuery {
  List<BalanceResponse> getAll();

  BalanceResponse getById(final Long id);

  BalanceRawContextResponse getRawContextByDriverIdAndQWeekId(Long driverId, Long qWeekId);

  BalanceResponse getRawCurrentByDriver(final Long driverId);

  BalanceResponse getLatest();

  BalanceResponse getByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  BigDecimal getRawBalanceTotalByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  Long getCountByDriver(final Long driverId);
}
