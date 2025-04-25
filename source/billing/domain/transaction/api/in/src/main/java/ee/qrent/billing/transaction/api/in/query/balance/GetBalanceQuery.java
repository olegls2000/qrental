package ee.qrent.billing.transaction.api.in.query.balance;

import ee.qrent.billing.transaction.api.in.response.balance.BalanceRawContextResponse;
import ee.qrent.billing.transaction.api.in.response.balance.BalanceResponse;

import java.util.List;

public interface GetBalanceQuery {
  List<BalanceResponse> getAll();

  BalanceResponse getById(final Long id);

  BalanceRawContextResponse getRawContextByDriverIdAndQWeekId(Long driverId, Long qWeekId);

  BalanceResponse getRawCurrentByDriver(final Long driverId);

  BalanceResponse getLatest();

  BalanceResponse getByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);

  Long getCountByDriver(final Long driverId);
}
