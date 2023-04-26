package ee.qrental.transaction.api.in.query;

import ee.qrental.transaction.api.in.response.balance.BalanceResponse;
import java.math.BigDecimal;
import java.util.List;

public interface GetDriverBalanceQuery {
  List<BalanceResponse> getAll();

  BigDecimal getTotalByDriverId(final Long driverId);
}
